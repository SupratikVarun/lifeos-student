package com.lifeos.student.service;

import com.lifeos.student.dto.AddDocumentDTO;
import com.lifeos.student.dto.DocumentResponseDTO;
import com.lifeos.student.entity.Document;
import com.lifeos.student.entity.User;
import com.lifeos.student.repository.DocumentRepository;
import com.lifeos.student.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository,
                           UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public DocumentResponseDTO addDocument(AddDocumentDTO request, String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = Document.builder()
                .fileName(request.getFileName())
                .fileUrl(request.getFileUrl())
                .fileSize(request.getFileSize())
                .user(user)
                .build();

        document = documentRepository.save(document);

        return mapToDTO(document);
    }

    public List<DocumentResponseDTO> getAllDocuments(String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return documentRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public DocumentResponseDTO getDocument(UUID id, String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = documentRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return mapToDTO(document);
    }

    public void deleteDocument(UUID id, String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = documentRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        documentRepository.delete(document);
    }

    private DocumentResponseDTO mapToDTO(Document document) {

        return DocumentResponseDTO.builder()
                .id(document.getId())
                .fileName(document.getFileName())
                .fileUrl(document.getFileUrl())
                .fileSize(document.getFileSize())
                .build();
    }
}