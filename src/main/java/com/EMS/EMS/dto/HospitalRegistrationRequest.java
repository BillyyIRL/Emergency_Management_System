package com.EMS.EMS.dto;

import com.EMS.EMS.enums.HospitalType;

public class HospitalRegistrationRequest {

    // Hospital details
    private String name;
    private String address;
    private String contactNumber;
    private Double latitude;
    private Double longitude;
    private Integer totalBeds;
    private HospitalType hospitalType;

    // Admin credentials
    private String adminEmail;
    private String adminPassword;
    private String adminFullName;
    private String adminPhoneNumber;

    public HospitalRegistrationRequest() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getTotalBeds() { return totalBeds; }
    public void setTotalBeds(Integer totalBeds) { this.totalBeds = totalBeds; }

    public HospitalType getHospitalType() { return hospitalType; }
    public void setHospitalType(HospitalType hospitalType) { this.hospitalType = hospitalType; }

    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }

    public String getAdminFullName() { return adminFullName; }
    public void setAdminFullName(String adminFullName) { this.adminFullName = adminFullName; }

    public String getAdminPhoneNumber() { return adminPhoneNumber; }
    public void setAdminPhoneNumber(String adminPhoneNumber) { this.adminPhoneNumber = adminPhoneNumber; }
}