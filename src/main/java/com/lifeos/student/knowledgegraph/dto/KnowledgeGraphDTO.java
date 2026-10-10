package com.lifeos.student.knowledgegraph.dto;

import lombok.Data;

import java.util.List;

@Data
public class KnowledgeGraphDTO {

    private List<KnowledgeNodeDTO> nodes;

    private List<KnowledgeRelationDTO> relations;

}