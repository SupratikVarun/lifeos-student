package com.lifeos.student.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AddNoteDTO {

    private String title;

    private String content;

    private String fileUrl;

    private UUID subjectId;
}