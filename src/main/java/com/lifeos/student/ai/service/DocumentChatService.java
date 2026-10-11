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

        if (document.getExtractedText() == null || document.getExtractedText().trim().isEmpty()) {
            return AIResponseDTO.builder()
                    .provider("System")
                    .response("⚠️ **No readable text found in this document.**\n\nThis PDF appears to be a scanned image or photograph of notes without selectable digital text. Apache PDFBox cannot extract text from pure images.\n\n💡 **Tip:** To chat with AI, please upload a digital PDF containing selectable text (such as typed notes, lecture slides, or exported documents).")
                    .fallbackUsed(false)
                    .processingTime(0)
                    .build();
        }

        System.out.println("========== PDF TEXT ==========");
        System.out.println(document.getExtractedText());
        System.out.println("==============================");

        AIRequestDTO aiRequest = new AIRequestDTO();

        aiRequest.setProvider(ProviderType.GEMINI);
        aiRequest.setText(document.getExtractedText());
        aiRequest.setQuestion(request.getQuestion());

        return aiManagerService.askQuestion(aiRequest);
    }

}