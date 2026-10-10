package com.lifeos.student.knowledgegraph.service;

import com.lifeos.student.knowledgegraph.dto.KnowledgeGraphDTO;

import java.util.UUID;

public interface KnowledgeGraphService {

    void generateKnowledgeGraph(
            UUID documentId,
            String email
    );

    KnowledgeGraphDTO getKnowledgeGraph(
            String email
    );

}