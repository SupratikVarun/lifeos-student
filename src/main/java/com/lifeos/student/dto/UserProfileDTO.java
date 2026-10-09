package com.lifeos.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    private String fullName;

    private String collegeEmail;

    private String hallTicketNumber;

    private String college;

    private String branch;

    private Integer year;

    private String profileImage;

    private String phoneNumber;

    private String bio;
}