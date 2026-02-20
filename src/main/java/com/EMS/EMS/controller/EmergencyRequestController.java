package com.EMS.EMS.controller;

import com.EMS.EMS.dto.EmergencyRequestResponse;
import com.EMS.EMS.dto.EmergencyResponseRequest;
import com.EMS.EMS.entity.EmergencyAssignment;
import com.EMS.EMS.entity.EmergencyRequest;
import com.EMS.EMS.entity.User;
import com.EMS.EMS.service.EmergencyRequestService;
import com.EMS.EMS.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emergency")
public class EmergencyRequestController {

    private final EmergencyRequestService emergencyRequestService;
    private final UserService userService;

    public EmergencyRequestController(EmergencyRequestService service, UserService userService) {
        this.emergencyRequestService = service;
        this.userService = userService;
    }

    // User places emergency request
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/request")
    public ResponseEntity<EmergencyRequestResponse> submitRequest(
            @RequestBody EmergencyRequest request,
            @AuthenticationPrincipal Long userId) {

        // Get user from JWT and attach to request
        User user = userService.getUserById(userId);
        request.setUser(user);

        EmergencyRequest saved = emergencyRequestService.createRequest(request);

        EmergencyRequestResponse response = new EmergencyRequestResponse(
                saved.getId(),
                saved.getLatitude(),
                saved.getLongitude(),
                saved.getPatientCount(),
                saved.getStatus().name(),
                saved.getFullyAssigned(),
                saved.getRequestTime()
        );

        return ResponseEntity.ok(response);
    }


    // GET ALL EMERGENCY REQUESTS
    // (hospital views incoming requests)
    @PreAuthorize("hasAnyRole('HOSPITAL_ADMIN', 'HOSPITAL_STAFF')")
    @GetMapping("/all")
    public ResponseEntity<List<EmergencyRequest>> getAllRequests() {
        return ResponseEntity.ok(emergencyRequestService.getAllRequests());
    }

    // Hospitals fetch pending requests
    @PreAuthorize("hasAnyRole('HOSPITAL_ADMIN', 'HOSPITAL_STAFF')")
    @GetMapping("/pending")
    public List<EmergencyRequest> getPendingRequests() {
        return emergencyRequestService.getPendingRequests();
    }

    // GET SINGLE EMERGENCY REQUEST BY ID
    @PreAuthorize("hasAnyRole('PATIENT', 'HOSPITAL_ADMIN', 'HOSPITAL_STAFF')")
    @GetMapping("/{id}")
    public ResponseEntity<EmergencyRequest> getRequestById(
            @PathVariable Long id) {
        return ResponseEntity.ok(emergencyRequestService.getRequestById(id));
    }

    // GET PARTIALLY ASSIGNED REQUESTS
    // (emergencies that still need more hospitals)
    @PreAuthorize("hasAnyRole('HOSPITAL_ADMIN', 'HOSPITAL_STAFF')")
    @GetMapping("/partial")
    public ResponseEntity<List<EmergencyRequest>> getPartialRequests() {
        return ResponseEntity.ok(emergencyRequestService.getPartiallyAssignedRequests());
    }

    // HOSPITAL VIEWS ONLY THEIR OWN REQUESTS
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<EmergencyAssignment>> getRequestsForHospital(
            @PathVariable Long hospitalId) {
        return ResponseEntity.ok(
                emergencyRequestService.getAssignmentsByHospital(hospitalId)
        );
    }

    // HOSPITAL RESPONDS TO EMERGENCY
    @PreAuthorize("hasAnyRole('HOSPITAL_ADMIN', 'HOSPITAL_STAFF')")
    @PostMapping("/respond")
    public ResponseEntity<EmergencyRequest> respondToEmergency(
            @RequestBody EmergencyResponseRequest response) {
        return ResponseEntity.ok(
                emergencyRequestService.respondToEmergency(response)
        );
    }
}

