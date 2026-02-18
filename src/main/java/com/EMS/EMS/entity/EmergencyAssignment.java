package com.EMS.EMS.entity;

import jakarta.persistence.*;

@Entity
public class EmergencyAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to EmergencyRequest
    @ManyToOne
    @JoinColumn(name = "emergency_request_id", nullable = false)
    private EmergencyRequest emergencyRequest;

    // Link to Hospital
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    // Number of patients sent to this hospital
    private Integer patientsAssigned;

    // ==============================
    // Getters and Setters
    // ==============================

    public Long getId() {
        return id;
    }

    public EmergencyRequest getEmergencyRequest() {
        return emergencyRequest;
    }

    public void setEmergencyRequest(EmergencyRequest emergencyRequest) {
        this.emergencyRequest = emergencyRequest;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Integer getPatientsAssigned() {
        return patientsAssigned;
    }

    public void setPatientsAssigned(Integer patientsAssigned) {
        this.patientsAssigned = patientsAssigned;
    }
}
