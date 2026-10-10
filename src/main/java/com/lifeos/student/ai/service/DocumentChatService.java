package com.lifeos.student.ai.service;

import com.lifeos.student.ai.dto.AIRequestDTO;
import com.lifeos.student.ai.dto.AIResponseDTO;
import com.lifeos.student.ai.dto.ChatRequestDTO;
import com.lifeos.student.ai.enums.ProviderType;
import com.lifeos.student.entity.Document;
import com.lifeos.student.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentChatService {

    private final DocumentService documentService;
    private final AIManagerService aiManagerService;

    public AIResponseDTO chatWithDocument(
            UUID documentId,
            String email,
            ChatRequestDTO request
    ) {

        Document document = documentService.getDocumentEntity(
                documentId,
                email
        );

        AIRequestDTO aiRequest = new AIRequestDTO();

        aiRequest.setProvider(ProviderType.GEMINI);
        aiRequest.setText(document.getExtractedText());
        aiRequest.setQuestion(request.getQuestion());

        return aiManagerService.askQuestion(aiRequest);
    }

}