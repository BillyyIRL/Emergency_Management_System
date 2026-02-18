package com.EMS.EMS.controller;

import com.EMS.EMS.entity.EmergencyRequest;
import com.EMS.EMS.service.EmergencyRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emergency")
public class EmergencyRequestController {

    private final EmergencyRequestService emergencyRequestService;

    public EmergencyRequestController(EmergencyRequestService service) {
        this.emergencyRequestService = service;
    }

    // User places emergency request
    @PostMapping("/request")
    public EmergencyRequest placeRequest(@RequestBody EmergencyRequest request) {
        return emergencyRequestService.createRequest(request);
    }

    // Hospitals fetch pending requests
    @GetMapping("/pending")
    public List<EmergencyRequest> getPendingRequests() {
        return emergencyRequestService.getPendingRequests();
    }
}
