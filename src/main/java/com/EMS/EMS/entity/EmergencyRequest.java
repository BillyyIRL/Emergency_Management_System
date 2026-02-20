package com.EMS.EMS.entity;

import com.EMS.EMS.enums.RequestStatus;
import com.EMS.EMS.enums.TransportMode;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "emergency_requests")
public class EmergencyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "emergency_type_id", nullable = false)
//    private EmergencyType emergencyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_hospital_id")
    private Hospital currentHospital;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column  // remove nullable = false
    private TransportMode transportMode;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    private int patientCount;

    private boolean isHandled;

    private Boolean fullyAssigned = false;

    public EmergencyRequest() {}

    public EmergencyRequest(User user,RequestStatus status,
                            Double latitude, Double longitude, TransportMode transportMode,
                            int patientCount) {
        this.user = user;
//        this.emergencyType = emergencyType;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.transportMode = transportMode;
        this.patientCount = patientCount;
        this.isHandled = false;
        this.requestTime = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.requestTime == null) this.requestTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
//    public EmergencyType getEmergencyType() { return emergencyType; }
//    public void setEmergencyType(EmergencyType emergencyType) { this.emergencyType = emergencyType; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    public Hospital getCurrentHospital() { return currentHospital; }
    public void setCurrentHospital(Hospital currentHospital) { this.currentHospital = currentHospital; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public TransportMode getTransportMode() { return transportMode; }
    public void setTransportMode(TransportMode transportMode) { this.transportMode = transportMode; }
    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }
    public int getPatientCount() { return patientCount; }
    public void setPatientCount(int patientCount) { this.patientCount = patientCount; }
    public boolean isHandled() { return isHandled; }
    public void setHandled(boolean handled) { this.isHandled = handled; }
    public Boolean getFullyAssigned() {
        return fullyAssigned;
    }
    public void setFullyAssigned(Boolean fullyAssigned) {
        this.fullyAssigned = fullyAssigned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmergencyRequest that = (EmergencyRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "EmergencyRequest{" +
                "id=" + id +
                ", status=" + status +
                ", requestTime=" + requestTime +
                ", patientCount=" + patientCount +
                ", isHandled=" + isHandled +
                '}';
    }
}


//ipkagun