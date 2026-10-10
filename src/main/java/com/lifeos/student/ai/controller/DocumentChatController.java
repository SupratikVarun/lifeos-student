package com.lifeos.student.ai.controller;

import com.lifeos.student.ai.dto.AIResponseDTO;
import com.lifeos.student.ai.dto.ChatRequestDTO;
import com.lifeos.student.ai.service.DocumentChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class DocumentChatController {

    private final DocumentChatService documentChatService;

    @PostMapping("/{documentId}")
    public AIResponseDTO chat(
            @PathVariable UUID documentId,
            @RequestBody ChatRequestDTO request,
            Authentication authentication
    ) {

        return documentChatService.chatWithDocument(
                documentId,
                authentication.getName(),
                request
        );
    }

}