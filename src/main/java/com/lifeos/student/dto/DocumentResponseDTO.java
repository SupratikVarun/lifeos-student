package com.lifeos.student.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseDTO {

    private UUID id;
    private String fileName;
    private String fileUrl;
    private Long fileSize;
}