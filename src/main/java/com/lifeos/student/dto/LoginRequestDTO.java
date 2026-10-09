package com.lifeos.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "College Email is required")
    @Email(message = "Enter a valid College Email")
    private String collegeEmail;

    @NotBlank(message = "Password is required")
    private String password;
}