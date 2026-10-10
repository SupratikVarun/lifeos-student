package com.lifeos.student.controller;

import com.lifeos.student.ai.dto.AIRequestDTO;
import com.lifeos.student.ai.dto.AIResponseDTO;
import com.lifeos.student.ai.enums.ProviderType;
import com.lifeos.student.ai.service.AIManagerService;
import com.lifeos.student.entity.Document;
import com.lifeos.student.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentAIController {

    private final DocumentService documentService;
    private final AIManagerService aiManagerService;

    @PostMapping("/{id}/summarize")
    public AIResponseDTO summarizeDocument(
            @PathVariable UUID id,
            Authentication authentication
    ) {

        Document document = documentService.getDocumentEntity(
                id,
                authentication.getName()
        );

        AIRequestDTO request = new AIRequestDTO();
        request.setProvider(ProviderType.AUTO);
        request.setText(document.getExtractedText());

        return aiManagerService.summarize(request);
    }

    @PostMapping("/{id}/explain")
    public AIResponseDTO explainDocument(
            @PathVariable UUID id,
            Authentication authentication
    ) {

        Document document = documentService.getDocumentEntity(
                id,
                authentication.getName()
        );

        AIRequestDTO request = new AIRequestDTO();
        request.setProvider(ProviderType.AUTO);
        request.setText(document.getExtractedText());

        return aiManagerService.explain(request);
    }

    @PostMapping("/{id}/flashcards")
    public AIResponseDTO flashcardsDocument(

            @PathVariable UUID id,
            Authentication authentication
    ) {

        Document document = documentService.getDocumentEntity(
                id,
                authentication.getName()
        );

        AIRequestDTO request = new AIRequestDTO();
        request.setProvider(ProviderType.AUTO);
        request.setText(document.getExtractedText());

        return aiManagerService.generateFlashcards(request);
    }

    @PostMapping("/{id}/quiz")
    public AIResponseDTO quizDocument(
            @PathVariable UUID id,
            Authentication authentication
    ) {

        Document document = documentService.getDocumentEntity(
                id,
                authentication.getName()
        );

        AIRequestDTO request = new AIRequestDTO();
        request.setProvider(ProviderType.AUTO);
        request.setText(document.getExtractedText());

        return aiManagerService.generateQuiz(request);
    }

    @PostMapping("/{id}/ask")
    public AIResponseDTO askQuestion(
            @PathVariable UUID id,
            @RequestBody AIRequestDTO request,
            Authentication authentication
    ) {

        Document document = documentService.getDocumentEntity(
                id,
                authentication.getName()
        );

        if (request.getProvider() == null) {
            request.setProvider(ProviderType.AUTO);
        }
        request.setText(document.getExtractedText());

        return aiManagerService.askQuestion(request);
    }
}