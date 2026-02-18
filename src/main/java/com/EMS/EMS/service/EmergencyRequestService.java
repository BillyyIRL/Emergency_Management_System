package com.EMS.EMS.service;

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

    @Autowired
    public EmergencyRequestService(
            EmergencyRequestRepository emergencyRequestRepository,
            HospitalService hospitalService,
            EmergencyAssignmentRepository emergencyAssignmentRepository) {

        this.emergencyRequestRepository = emergencyRequestRepository;
        this.hospitalService = hospitalService;
        this.emergencyAssignmentRepository = emergencyAssignmentRepository;
    }


    @Transactional
    public EmergencyRequest createRequest(EmergencyRequest request) {
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
}