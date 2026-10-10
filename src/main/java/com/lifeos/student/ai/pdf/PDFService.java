package com.lifeos.student.ai.pdf;

import org.springframework.web.multipart.MultipartFile;

public interface PDFService {

    String savePdf(MultipartFile file);

    String extractText(MultipartFile file);

}