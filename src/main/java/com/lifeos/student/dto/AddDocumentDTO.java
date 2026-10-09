package com.lifeos.student.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddDocumentDTO {

    private String fileName;
    private String fileUrl;
    private Long fileSize;
}