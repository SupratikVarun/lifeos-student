package com.lifeos.student.dto;

import lombok.Data;

@Data
public class UpdateNoteDTO {

    private String title;

    private String content;

    private String fileUrl;
}