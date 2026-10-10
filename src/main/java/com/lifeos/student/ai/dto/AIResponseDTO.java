package com.lifeos.student.ai.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AIResponseDTO {

    private String provider;

    private String response;

    private boolean fallbackUsed;

    private long processingTime;
}