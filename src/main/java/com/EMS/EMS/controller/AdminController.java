package com.EMS.EMS.controller;


import com.EMS.EMS.entity.Hospital;
import com.EMS.EMS.enums.HospitalStatus;
import com.EMS.EMS.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final HospitalService hospitalService;

    public AdminController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }


    //get the list of pending hospitals
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/hospitals/pending")
    public ResponseEntity<List<Hospital>> getPendingHospitals() {
        return ResponseEntity.ok(hospitalService.getPendingHospitals());
    }


    //approve PENDING hospitals
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/hospitals/{hospitalId}/approve")
    public ResponseEntity<Hospital> approveHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(
                hospitalService.updateHospitalStatus(hospitalId, HospitalStatus.APPROVED)
        );
    }


    //Reject PENDING hospitals
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/hospitals/{hospitalId}/reject")
    public ResponseEntity<Hospital> rejectHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(
                hospitalService.updateHospitalStatus(hospitalId, HospitalStatus.REJECTED)
        );
    }


    //get ALL hospitals i.e PENDING, APPROVED. REJECTED
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/hospitals")
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }
}