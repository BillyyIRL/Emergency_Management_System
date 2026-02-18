package com.EMS.EMS.controller;

import com.EMS.EMS.dto.LoginRequest;
import com.EMS.EMS.dto.RegisterRequest;
import com.EMS.EMS.entity.User;
import com.EMS.EMS.security.JwtService;
import com.EMS.EMS.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Registration endpoint
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        User savedUser = userService.register(request);
        return "User registered successfully: " + savedUser.getEmail();
    }

    // Login endpoint
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        User user = userService.getUserByEmail(request.getEmail());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate token using JwtService
        String token = jwtService.generateToken(user.getId(), user.getRole());

        return token;
    }
}
