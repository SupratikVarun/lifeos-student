package com.lifeos.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "College Email is required")
    @Email(message = "Enter a valid College Email")
    private String collegeEmail;

    @NotBlank(message = "Hall Ticket Number is required")
    private String hallTicketNumber;

    @NotBlank(message = "College Name is required")
    private String college;

    @NotBlank(message = "Branch is required")
    private String branch;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;
}