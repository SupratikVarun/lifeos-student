package com.lifeos.student.knowledgegraph.dto;

import com.lifeos.student.knowledgegraph.enums.RelationType;
import lombok.Data;

@Data
public class KnowledgeRelationDTO {

    private String source;

    private String target;

    private RelationType relation;

}