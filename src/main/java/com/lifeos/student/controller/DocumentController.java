package com.lifeos.student.controller;

import com.lifeos.student.dto.AddDocumentDTO;
import com.lifeos.student.dto.DocumentResponseDTO;
import com.lifeos.student.service.DocumentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DocumentResponseDTO uploadDocument(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        return documentService.uploadDocument(
                file,
                authentication.getName()
        );
    }
    @GetMapping
    public List<DocumentResponseDTO> getAllDocuments(
            Authentication authentication) {

        return documentService.getAllDocuments(
                authentication.getName()
        );
    }

    @GetMapping("/{id}")
    public DocumentResponseDTO getDocument(
            @PathVariable UUID id,
            Authentication authentication) {

        return documentService.getDocument(
                id,
                authentication.getName()
        );
    }

    @DeleteMapping("/{id}")
    public String deleteDocument(
            @PathVariable UUID id,
            Authentication authentication) {

        documentService.deleteDocument(
                id,
                authentication.getName()
        );

        return "Document deleted successfully.";
    }
}