package com.lifeos.student.knowledgegraph.dto;

import com.lifeos.student.knowledgegraph.enums.NodeType;
import lombok.Data;

@Data
public class KnowledgeNodeDTO {

    private String name;

    private NodeType type;

}