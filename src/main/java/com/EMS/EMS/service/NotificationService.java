package com.EMS.EMS.service;

import com.EMS.EMS.entity.EmergencyRequest;
import com.EMS.EMS.entity.Hospital;
import com.EMS.EMS.entity.Notification;
import com.EMS.EMS.entity.User;
import com.EMS.EMS.enums.TransportMode;
import com.EMS.EMS.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    public NotificationService(SimpMessagingTemplate messagingTemplate,
                               NotificationRepository notificationRepository) {
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = notificationRepository;
    }


    public void notifyHospital(Hospital hospital, EmergencyRequest request, int patientsAssigned) {

        // Build notification message
        Map<String, Object> message = new HashMap<>();
        message.put("type", "NEW_EMERGENCY");
        message.put("hospitalId", hospital.getId());
        message.put("emergencyRequestId", request.getId());
        message.put("patientsAssigned", patientsAssigned);
        message.put("latitude", request.getLatitude());
        message.put("longitude", request.getLongitude());
        message.put("patientCount", request.getPatientCount());
        message.put("timestamp", LocalDateTime.now().toString());

        // Save notification to DB
        Notification notification = new Notification();
        notification.setHospitalId(hospital.getId());
        notification.setMessage("New emergency request: " + patientsAssigned + " patient(s) assigned to your hospital");
        notification.setEmergencyRequestId(request.getId());
        notification.setRead(false);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);

        messagingTemplate.convertAndSend(
                "/topic/hospital/" + hospital.getId(),
                (Object) message
        );
    }

    public void notifyPatient(User patient, Hospital hospital,
                              TransportMode transportMode, String status) {

        Map<String, Object> message = new HashMap<>();
        message.put("type", "EMERGENCY_UPDATE");
        message.put("status", status);
        message.put("hospitalName", hospital.getName());
        message.put("hospitalLat", hospital.getLatitude());
        message.put("hospitalLng", hospital.getLongitude());
        message.put("transportMode", transportMode != null ? transportMode.name() : "PENDING");
        message.put("timestamp", LocalDateTime.now().toString());

        // Save notification to DB
        Notification notification = new Notification();
        notification.setHospitalId(hospital.getId());
        notification.setMessage("Your emergency request has been " + status +
                " by " + hospital.getName());
        notification.setSentAt(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);

        // Push real time notification to patient
        messagingTemplate.convertAndSend(
                "/topic/patient/" + patient.getId(),
                (Object) message
        );
    }


    //
    public void notifyPatientTransport(User patient, Hospital hospital,
                                       TransportMode transportMode,
                                       Double targetLat, Double targetLng) {

        Map<String, Object> message = new HashMap<>();
        message.put("type", "TRANSPORT_UPDATE");
        message.put("transportMode", transportMode.name());
        message.put("hospitalName", hospital.getName());
        message.put("hospitalContact", hospital.getContactNumber());
        message.put("targetLatitude", targetLat);
        message.put("targetLongitude", targetLng);
        message.put("timestamp", LocalDateTime.now().toString());

        if (transportMode == TransportMode.AMBULANCE) {
            message.put("instruction", "Stay at your location. Ambulance is on the way.");
        } else {
            message.put("instruction", "Please drive to the hospital location provided.");
        }

        // Save to DB
        Notification notification = new Notification();
        notification.setHospitalId(hospital.getId());
        notification.setMessage("Transport update: " + transportMode.name() +
                " - " + hospital.getName());
        notification.setSentAt(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);

        // Push to patient in real time
        messagingTemplate.convertAndSend(
                "/topic/patient/" + patient.getId(),
                (Object) message
        );
    }
}