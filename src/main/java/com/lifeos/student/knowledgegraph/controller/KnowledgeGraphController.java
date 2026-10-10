package com.lifeos.student.knowledgegraph.controller;

import com.lifeos.student.knowledgegraph.dto.KnowledgeGraphDTO;
import com.lifeos.student.knowledgegraph.service.KnowledgeGraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/knowledge-graph")
@RequiredArgsConstructor
public class KnowledgeGraphController {

    private final KnowledgeGraphService knowledgeGraphService;

    @PostMapping("/{documentId}/generate")
    public String generateKnowledgeGraph(
            @PathVariable UUID documentId,
            Authentication authentication
    ) {

        knowledgeGraphService.generateKnowledgeGraph(
                documentId,
                authentication.getName()
        );

        return "Knowledge Graph Generated Successfully";
    }

    @GetMapping
    public KnowledgeGraphDTO getKnowledgeGraph(
            Authentication authentication
    ) {

        return knowledgeGraphService.getKnowledgeGraph(
                authentication.getName()
        );

    }

}