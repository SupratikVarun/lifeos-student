package com.lifeos.student.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCalenderEventDTO {

    private String title;
    private String description;
    private LocalDate eventDate;
    private String eventType;

}