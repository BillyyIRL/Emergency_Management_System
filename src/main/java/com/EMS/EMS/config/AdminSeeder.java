package com.EMS.EMS.config;

import com.EMS.EMS.entity.User;
import com.EMS.EMS.enums.Role;
import com.EMS.EMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.fullName}")
    private String adminFullName;

    @Value("${admin.phoneNumber}")
    private String adminPhoneNumber;

    @Bean
    public CommandLineRunner seedSuperAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Only create if it doesn't already exist
            if (!userRepository.existsByEmail(adminEmail)) {
                User superAdmin = new User(
                        adminFullName,
                        adminEmail,
                        adminPhoneNumber,
                        passwordEncoder.encode(adminPassword),
                        Role.SUPER_ADMIN
                );
                userRepository.save(superAdmin);
                System.out.println("Super admin account created successfully");
            } else {
                System.out.println("Super admin already exists, skipping");
            }
        };
    }
}