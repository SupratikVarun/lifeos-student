package com.lifeos.student.dto;

import lombok.Data;

@Data
public class UpdateProfileDTO {

    private String fullName;

    private String college;

    private String branch;

    private Integer year;

    private String profileImage;

    private String phoneNumber;

    private String bio;
}