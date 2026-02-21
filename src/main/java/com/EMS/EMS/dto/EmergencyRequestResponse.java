package com.EMS.EMS.dto;

import java.time.LocalDateTime;

public class EmergencyRequestResponse {

    private Long id;
    private Double latitude;
    private Double longitude;
    private Integer patientCount;
    private String status;
    private Boolean fullyAssigned;
    private LocalDateTime requestTime;
    private String transportMode;
    private String assignedHospital;

    public EmergencyRequestResponse(Long id, Double latitude, Double longitude,
                                    Integer patientCount, String status,
                                    Boolean fullyAssigned, LocalDateTime requestTime,
                                    String transportMode, String assignedHospital) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.patientCount = patientCount;
        this.status = status;
        this.fullyAssigned = fullyAssigned;
        this.requestTime = requestTime;
        this.transportMode = transportMode;
        this.assignedHospital = assignedHospital;
    }

    public Long getId() { return id; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Integer getPatientCount() { return patientCount; }
    public String getStatus() { return status; }
    public Boolean getFullyAssigned() { return fullyAssigned; }
    public LocalDateTime getRequestTime() { return requestTime; }
    public String getTransportMode() { return transportMode; }
    public String getAssignedHospital() { return assignedHospital; }
}