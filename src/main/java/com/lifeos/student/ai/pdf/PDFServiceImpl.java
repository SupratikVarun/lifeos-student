package com.lifeos.student.ai.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PDFServiceImpl implements PDFService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String savePdf(MultipartFile file) {

        try {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/" + file.getOriginalFilename();

        } catch (IOException e) {

            e.printStackTrace();

            throw new RuntimeException("Failed to save PDF", e);

        }
    }

    @Override
    public String extractText(MultipartFile file) {

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {

            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(document);

            System.out.println("=========== PDF TEXT ===========");
            System.out.println(text);
            System.out.println("================================");

            return text;

        } catch (IOException e) {

            throw new RuntimeException("Failed to extract PDF text", e);

        }
    }
}