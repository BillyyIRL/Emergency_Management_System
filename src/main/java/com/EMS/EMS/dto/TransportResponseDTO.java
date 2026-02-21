package com.EMS.EMS.dto;

public class TransportResponseDTO {

    private Long emergencyRequestId;
    private String hospitalName;
    private Double hospitalLatitude;
    private Double hospitalLongitude;
    private String transportMode;
    private String message;

    public TransportResponseDTO() {}

    public TransportResponseDTO(Long emergencyRequestId, String hospitalName,
                                Double hospitalLatitude, Double hospitalLongitude,
                                String transportMode, String message) {
        this.emergencyRequestId = emergencyRequestId;
        this.hospitalName = hospitalName;
        this.hospitalLatitude = hospitalLatitude;
        this.hospitalLongitude = hospitalLongitude;
        this.transportMode = transportMode;
        this.message = message;
    }

    public Long getEmergencyRequestId() { return emergencyRequestId; }
    public void setEmergencyRequestId(Long emergencyRequestId) { this.emergencyRequestId = emergencyRequestId; }

    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    public Double getHospitalLatitude() { return hospitalLatitude; }
    public void setHospitalLatitude(Double hospitalLatitude) { this.hospitalLatitude = hospitalLatitude; }

    public Double getHospitalLongitude() { return hospitalLongitude; }
    public void setHospitalLongitude(Double hospitalLongitude) { this.hospitalLongitude = hospitalLongitude; }

    public String getTransportMode() { return transportMode; }
    public void setTransportMode(String transportMode) { this.transportMode = transportMode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
