package com.lifeos.student.dto;

import lombok.Data;

@Data
public class UpdateSubjectDTO {

    private String subjectName;

    private String subjectCode;

    private String facultyName;

    private String semester;
}