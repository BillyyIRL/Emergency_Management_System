package com.EMS.EMS.controller;

import com.EMS.EMS.dto.HospitalRegistrationRequest;
import com.EMS.EMS.entity.Hospital;
import com.EMS.EMS.enums.HospitalStatus;
import com.EMS.EMS.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;
    private HospitalRegistrationRequest hospitalRegistrationRequest;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    // Register a hospital
    @PostMapping("/register")
    public Hospital register(@RequestBody HospitalRegistrationRequest request) {
        return hospitalService.registerHospital(request);
    }

    // Get hospital by ID
    @GetMapping("/{id}")
    public Hospital getHospital(@PathVariable Long id) {
        return hospitalService.getHospitalById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));
    }

    // Update available beds
    @PutMapping("/{id}/beds")
    public Hospital updateBeds(@PathVariable Long id, @RequestParam int beds) {
        return hospitalService.updateAvailableBeds(id, beds);
    }

    // Get all active hospitals
    @GetMapping("/active")
    public List<Hospital> getAllActiveHospitals() {
        return hospitalService.getAllApprovedHospitals();
    }

    @GetMapping("/nearest")
    public List<Hospital> getNearestHospitals(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") int limit) {

        return hospitalService.findNearestApprovedHospitals(latitude, longitude, limit);
    }

    // ==============================
    // ADDED: Approve or Reject hospital (Admin)
    // ==============================

    @PutMapping("/{id}/status")
    public Hospital updateStatus(
            @PathVariable Long id,
            @RequestParam HospitalStatus hospitalStatus) {
        return hospitalService.updateHospitalStatus(id, hospitalStatus);
    }

    // ==============================
    // ADDED: Get all approved hospitals
    // ==============================

    @GetMapping("/approved")
    public List<Hospital> getAllApprovedHospitals() {
        return hospitalService.getAllApprovedHospitals();
    }
}