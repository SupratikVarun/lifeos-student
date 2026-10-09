package com.lifeos.student.service;

import com.lifeos.student.dto.UpdateProfileDTO;
import com.lifeos.student.dto.UserProfileDTO;
import com.lifeos.student.entity.User;
import com.lifeos.student.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileDTO getProfile(String collegeEmail) {

        User user = userRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserProfileDTO.builder()
                .fullName(user.getFullName())
                .collegeEmail(user.getCollegeEmail())
                .hallTicketNumber(user.getHallTicketNumber())
                .college(user.getCollege())
                .branch(user.getBranch())
                .year(user.getYear())
                .profileImage(user.getProfileImage())
                .phoneNumber(user.getPhoneNumber())
                .bio(user.getBio())
                .build();
    }

    public UserProfileDTO updateProfile(String collegeEmail,
                                        UpdateProfileDTO request) {

        User user = userRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setCollege(request.getCollege());
        user.setBranch(request.getBranch());
        user.setYear(request.getYear());
        user.setProfileImage(request.getProfileImage());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBio(request.getBio());

        userRepository.save(user);

        return getProfile(collegeEmail);
    }
}