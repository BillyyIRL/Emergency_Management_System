package com.EMS.EMS.service;

import com.EMS.EMS.dto.HospitalRegistrationRequest;
import com.EMS.EMS.entity.Hospital;
import com.EMS.EMS.entity.User;
import com.EMS.EMS.enums.HospitalStatus;
import com.EMS.EMS.enums.Role;
import com.EMS.EMS.repository.HospitalRepository;
import com.EMS.EMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Comparator;


import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public HospitalService(HospitalRepository hospitalRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.hospitalRepository = hospitalRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a hospital
    public Hospital registerHospital(HospitalRegistrationRequest request) {

        // Check if email is already registered
        if (userRepository.existsByEmail(request.getAdminEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create the hospital admin user account
        User adminUser = new User(
                request.getAdminFullName(),
                request.getAdminEmail(),
                request.getAdminPhoneNumber(),
                passwordEncoder.encode(request.getAdminPassword()),
                Role.HOSPITAL_ADMIN
        );
        userRepository.save(adminUser);

        // Create the hospital
        Hospital hospital = new Hospital();
        hospital.setName(request.getName());
        hospital.setAddress(request.getAddress());
        hospital.setContactNumber(request.getContactNumber());
        hospital.setLatitude(request.getLatitude());
        hospital.setLongitude(request.getLongitude());
        hospital.setTotalBeds(request.getTotalBeds());
        hospital.setAvailableBeds(request.getTotalBeds());
        hospital.setHospitalType(request.getHospitalType());
        hospital.setHospitalStatus(HospitalStatus.PENDING);
        hospital.setIsActive(false);

        return hospitalRepository.save(hospital);
    }


    //update the number of available bed spaces
    public Hospital updateAvailableBeds(Long hospitalId, int availableBeds) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + hospitalId));

        if (availableBeds > hospital.getTotalBeds()) {
            throw new RuntimeException("Available beds cannot exceed total beds of " + hospital.getTotalBeds());
        }

        if (availableBeds < 0) {
            throw new RuntimeException("Available beds cannot be negative");
        }

        hospital.setAvailableBeds(availableBeds);
        return hospitalRepository.save(hospital);
    }


    // findNearestApprovedHospitals
    public List<Hospital> findNearestApprovedHospitals(double userLat, double userLon, int maxResults) {

        List<Hospital> approvedHospitals = hospitalRepository.findByHospitalStatus(HospitalStatus.APPROVED);

        approvedHospitals.sort(Comparator.comparingDouble(
                hospital -> haversineDistance(userLat, userLon, hospital.getLatitude(), hospital.getLongitude())
        ));

        return approvedHospitals.stream()
                .limit(maxResults)
                .toList();
    }

    public Hospital updateHospitalStatus(Long hospitalId, HospitalStatus newStatus) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + hospitalId));

        hospital.setHospitalStatus(newStatus);
        return hospitalRepository.save(hospital);
    }

    // Haversine formula to calculate distance in kilometers
    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) *
                        Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public List<Hospital> getAllApprovedHospitals() {
        return hospitalRepository.findByHospitalStatus(HospitalStatus.APPROVED);
    }

    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Optional<Hospital> getHospitalById(Long hospitalId) {
        return hospitalRepository.findById(hospitalId);
    }
}