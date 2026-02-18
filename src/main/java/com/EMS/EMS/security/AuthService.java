package com.EMS.EMS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EMS.EMS.dto.LoginRequest;
import com.EMS.EMS.dto.RegisterRequest;
import com.EMS.EMS.entity.User;
import com.EMS.EMS.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder encoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }



    // Function to register a new user in the system.
    public void register(RegisterRequest request) {
        // check if user already exists in repo
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
    }



    // Function to log in a user.
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // checks to know if the password credential entered is
        // valid or inline with the one the user stored in the database
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user.getId(), user.getRole());
    }

}
