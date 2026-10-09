package com.lifeos.student.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CalenderEventResponseDTO {

    private UUID id;

    private String title;

    private String description;

    private LocalDate eventDate;

    private String eventType;

}