package com.EMS.EMS.service;

import com.EMS.EMS.dto.EmergencyResponseRequest;
import com.EMS.EMS.entity.EmergencyAssignment;
import com.EMS.EMS.entity.EmergencyRequest;
import com.EMS.EMS.entity.Hospital;
import com.EMS.EMS.enums.RequestStatus;
import com.EMS.EMS.repository.EmergencyAssignmentRepository;
import com.EMS.EMS.repository.EmergencyRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyRequestService {

    private final EmergencyRequestRepository emergencyRequestRepository;
    private final HospitalService hospitalService;
    private final EmergencyAssignmentRepository emergencyAssignmentRepository;
    private final NotificationService notificationService;

    @Autowired
    public EmergencyRequestService(
            EmergencyRequestRepository emergencyRequestRepository,
            HospitalService hospitalService,
            EmergencyAssignmentRepository emergencyAssignmentRepository, NotificationService notificationService) {

        this.emergencyRequestRepository = emergencyRequestRepository;
        this.hospitalService = hospitalService;
        this.emergencyAssignmentRepository = emergencyAssignmentRepository;
        this.notificationService = notificationService;
    }


    @Transactional
    public EmergencyRequest createRequest(EmergencyRequest request) {

        // Set initial status before saving
        request.setStatus(RequestStatus.PENDING);
        request.setFullyAssigned(false);

        EmergencyRequest saved = emergencyRequestRepository.save(request);
        routePatients(saved);
        return saved;
    }


    private void routePatients(EmergencyRequest request) {

        int remainingPatients = request.getPatientCount();

        List<Hospital> hospitals = hospitalService.findNearestApprovedHospitals(
                request.getLatitude(),
                request.getLongitude(),
                10
        );

        for (Hospital hospital : hospitals) {

            if (remainingPatients <= 0) break;

            int availableBeds = hospital.getAvailableBeds();

            if (availableBeds > 0) {

                int patientsAssigned = Math.min(availableBeds, remainingPatients);

                hospital.setAvailableBeds(availableBeds - patientsAssigned);
                hospitalService.saveHospital(hospital);

                EmergencyAssignment assignment = new EmergencyAssignment();
                assignment.setEmergencyRequest(request);
                assignment.setHospital(hospital);
                assignment.setPatientsAssigned(patientsAssigned);
                emergencyAssignmentRepository.save(assignment);

                // Notify hospital immediately after assignment
                notificationService.notifyHospital(hospital, request, patientsAssigned);

                remainingPatients -= patientsAssigned;
            }
        }

        request.setFullyAssigned(remainingPatients <= 0);
        emergencyRequestRepository.save(request);
    }


    public List<EmergencyRequest> getAllRequests() {
        return emergencyRequestRepository.findAll();
    }

    public List<EmergencyRequest> getPartiallyAssignedRequests() {
        return emergencyRequestRepository.findByFullyAssigned(false);
    }

    public List<EmergencyRequest> getPendingRequests() {
        return emergencyRequestRepository.findByStatus(RequestStatus.PENDING);
    }

    public EmergencyRequest getRequestById(Long id) {
        return emergencyRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emergency request not found with id: " + id));
    }

    public List<EmergencyAssignment> getAssignmentsByHospital(Long hospitalId) {
        return emergencyAssignmentRepository.findByHospitalId(hospitalId);
    }

    @Transactional
    public EmergencyRequest respondToEmergency(EmergencyResponseRequest response) {

        // Get the emergency request
        EmergencyRequest request = emergencyRequestRepository.findById(response.getEmergencyRequestId())
                .orElseThrow(() -> new RuntimeException("Emergency request not found"));

        // Get the hospital
        Hospital hospital = hospitalService.getHospitalById(response.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        if (response.isAccepted()) {

            // Hospital accepted the request
            request.setStatus(RequestStatus.ACCEPTED);
            request.setCurrentHospital(hospital);
            request.setTransportMode(response.getTransportMode());

            // Notify patient that hospital accepted
            notificationService.notifyPatient(
                    request.getUser(),
                    hospital,
                    response.getTransportMode(),
                    "ACCEPTED"
            );

        } else {

            // Hospital rejected the request
            request.setStatus(RequestStatus.REJECTED);

            // Free up the beds that were previously assigned
            EmergencyAssignment assignment = emergencyAssignmentRepository
                    .findByEmergencyRequestIdAndHospitalId(
                            request.getId(),
                            hospital.getId()
                    );

            if (assignment != null) {
                // Give beds back to hospital
                hospital.setAvailableBeds(
                        hospital.getAvailableBeds() + assignment.getPatientsAssigned()
                );
                hospitalService.saveHospital(hospital);
            }

            // Reroute to next nearest hospital
            request.setStatus(RequestStatus.REROUTED);
            emergencyRequestRepository.save(request);
            routePatients(request);

            // Notify patient of rerouting
            notificationService.notifyPatient(
                    request.getUser(),
                    hospital,
                    null,
                    "REJECTED"
            );
        }

        return emergencyRequestRepository.save(request);
    }
}