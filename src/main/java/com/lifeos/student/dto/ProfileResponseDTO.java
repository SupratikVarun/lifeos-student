package com.lifeos.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {

    private String fullName;
    private String collegeEmail;
    private String hallTicketNumber;
    private String college;
    private String branch;
    private Integer year;
    private String profileImage;
}