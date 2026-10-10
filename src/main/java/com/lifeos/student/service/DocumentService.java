package com.lifeos.student.service;

import com.lifeos.student.dto.AddDocumentDTO;
import com.lifeos.student.dto.DocumentResponseDTO;
import com.lifeos.student.entity.Document;
import com.lifeos.student.entity.User;
import com.lifeos.student.repository.DocumentRepository;
import com.lifeos.student.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.lifeos.student.ai.pdf.PDFService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final PDFService pdfService;

    public DocumentService(DocumentRepository documentRepository,
                           UserRepository userRepository,
                           PDFService pdfService) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.pdfService = pdfService;
    }

    @Transactional
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
    public DocumentResponseDTO uploadDocument(
            MultipartFile file,
            String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String filePath = pdfService.savePdf(file);
        String extractedText = pdfService.extractText(file);
        System.out.println("=======================================");
        System.out.println("Extracted Text:");
        System.out.println(extractedText);
        System.out.println("Length: " + extractedText.length());
        System.out.println("=======================================");
        Document document = Document.builder()
                .fileName(file.getOriginalFilename())
                .fileUrl(filePath)
                .fileSize(file.getSize())
                .extractedText(extractedText)
                .user(user)
                .build();

        document = documentRepository.save(document);

        return mapToDTO(document);
    }

    public Document getDocumentEntity(UUID id, String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return documentRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }


}