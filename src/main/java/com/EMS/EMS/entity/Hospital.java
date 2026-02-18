package com.EMS.EMS.entity;

import com.EMS.EMS.enums.HospitalType;
import jakarta.persistence.*;
import java.util.Objects;

import com.EMS.EMS.enums.HospitalStatus;

@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private HospitalStatus hospitalStatus;

    private int totalBeds;

    private int availableBeds;

    @Enumerated(EnumType.STRING)
    private HospitalType hospitalType;

    @ManyToMany
    @JoinTable(
        name = "hospital_emergency_types",
        joinColumns = @JoinColumn(name = "hospital_id"),
        inverseJoinColumns = @JoinColumn(name = "emergency_type_id")
    )
    private java.util.Set<EmergencyType> supportedEmergencyTypes;

    public Hospital() {
    }

    public Hospital(String name, String address, String contactNumber, Double latitude, Double longitude, Boolean isActive, HospitalType hospitalType) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isActive = isActive;
        this.hospitalType = hospitalType;
        this.totalBeds = 0;
        this.availableBeds = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public java.util.Set<EmergencyType> getSupportedEmergencyTypes() {
        return supportedEmergencyTypes;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public HospitalStatus getHospitalAvailabilityStatus() {
        return hospitalStatus;
    }

    public void setHospitalAvailabilityStatus(HospitalStatus hospitalStatus) {
        this.hospitalStatus = hospitalStatus;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public void setSupportedEmergencyTypes(java.util.Set<EmergencyType> supportedEmergencyTypes) {
        this.supportedEmergencyTypes = supportedEmergencyTypes;
    }

    public HospitalType getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(HospitalType hospitalType) {
        this.hospitalType = hospitalType;
    }

    public HospitalStatus getHospitalStatus() {
        return hospitalStatus;
    }

    public void setHospitalStatus(HospitalStatus hospitalStatus) {
        this.hospitalStatus = hospitalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospital hospital = (Hospital) o;
        return Objects.equals(id, hospital.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", isActive=" + isActive +
                '}';
    }
}
