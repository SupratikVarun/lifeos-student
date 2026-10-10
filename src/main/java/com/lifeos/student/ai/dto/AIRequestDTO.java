package com.lifeos.student.ai.dto;

import com.lifeos.student.ai.enums.ProviderType;
import lombok.Data;

@Data
public class AIRequestDTO {

    private ProviderType provider;

    private String text;

    private String question;


}