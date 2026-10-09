package com.lifeos.student.service;
import com.lifeos.student.dto.LoginRequestDTO;
import com.lifeos.student.dto.AuthResponseDTO;
import com.lifeos.student.dto.RegisterRequestDTO;
import com.lifeos.student.entity.User;
import com.lifeos.student.repository.UserRepository;
import com.lifeos.student.security.JWTService;
import com.lifeos.student.dto.UpdateProfileDTO;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByCollegeEmail(request.getCollegeEmail())) {
            throw new RuntimeException("College email is already registered.");
        }

        if (userRepository.existsByHallTicketNumber(request.getHallTicketNumber())) {
            throw new RuntimeException("Hall ticket number is already registered.");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setCollegeEmail(request.getCollegeEmail());
        user.setHallTicketNumber(request.getHallTicketNumber());
        user.setCollege(request.getCollege());
        user.setBranch(request.getBranch());
        user.setYear(request.getYear());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return new AuthResponseDTO(
                true,
                "Student registered successfully.",
                null
        );
    }

    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByCollegeEmail(request.getCollegeEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password.");
        }

        String token = jwtService.generateToken(user.getCollegeEmail());

        return new AuthResponseDTO(
                true,
                "Login successful.",
                token
        );
    }



}