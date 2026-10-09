package com.lifeos.student.controller;

import com.lifeos.student.dto.UpdateProfileDTO;
import com.lifeos.student.dto.UserProfileDTO;
import com.lifeos.student.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET PROFILE
    @GetMapping("/profile")
    public UserProfileDTO getProfile(Authentication authentication) {

        String collegeEmail = authentication.getName();

        return userService.getProfile(collegeEmail);
    }

    // UPDATE PROFILE
    @PutMapping("/profile")
    public UserProfileDTO updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileDTO request) {

        String collegeEmail = authentication.getName();

        return userService.updateProfile(collegeEmail, request);
    }
}