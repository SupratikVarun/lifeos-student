package com.lifeos.student.search.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SearchResultDTO {

    private UUID id;

    private String type;

    private String title;

    private String description;
}