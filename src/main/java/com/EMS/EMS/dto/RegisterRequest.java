package com.EMS.EMS.dto;


import com.EMS.EMS.enums.Role;

public class RegisterRequest {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role; // PATIENT or HOSPITAL_ADMIN

    // Default constructor
    public RegisterRequest() {
    }

    // Full constructor
    public RegisterRequest(String fullName, String email, String phoneNumber, String password, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    // Getters & Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
