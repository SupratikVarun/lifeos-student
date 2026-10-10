package com.lifeos.student.search.service;

import com.lifeos.student.repository.DocumentRepository;
import com.lifeos.student.repository.NoteRepository;
import com.lifeos.student.repository.SubjectRepository;
import com.lifeos.student.repository.UserRepository;
import com.lifeos.student.knowledgegraph.repository.KnowledgeNodeRepository;
import com.lifeos.student.search.dto.SearchResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lifeos.student.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final UserRepository userRepository;

    private final SubjectRepository subjectRepository;

    private final NoteRepository noteRepository;

    private final DocumentRepository documentRepository;

    private final KnowledgeNodeRepository knowledgeNodeRepository;

    @Override
    public List<SearchResultDTO> search(
            String keyword,
            String email
    ) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SearchResultDTO> results = new ArrayList<>();

        // SUBJECTS
        subjectRepository
                .findByUserAndSubjectNameContainingIgnoreCase(user, keyword)
                .forEach(subject -> results.add(
                        SearchResultDTO.builder()
                                .id(subject.getId())
                                .type("SUBJECT")
                                .title(subject.getSubjectName())
                                .description(subject.getSubjectCode())
                                .build()
                ));

        // NOTES (TITLE)
        noteRepository
                .findByUserAndTitleContainingIgnoreCase(user, keyword)
                .forEach(note -> results.add(
                        SearchResultDTO.builder()
                                .id(note.getId())
                                .type("NOTE")
                                .title(note.getTitle())
                                .description(note.getContent())
                                .build()
                ));

        // NOTES (CONTENT)
        noteRepository
                .findByUserAndContentContainingIgnoreCase(user, keyword)
                .forEach(note -> {

                    boolean alreadyExists = results.stream()
                            .anyMatch(r -> r.getId().equals(note.getId()));

                    if (!alreadyExists) {
                        results.add(
                                SearchResultDTO.builder()
                                        .id(note.getId())
                                        .type("NOTE")
                                        .title(note.getTitle())
                                        .description(note.getContent())
                                        .build()
                        );
                    }

                });

        // DOCUMENTS
        documentRepository
                .findByUserAndFileNameContainingIgnoreCase(user, keyword)
                .forEach(document -> results.add(
                        SearchResultDTO.builder()
                                .id(document.getId())
                                .type("DOCUMENT")
                                .title(document.getFileName())
                                .description(document.getFileUrl())
                                .build()
                ));
        documentRepository
                .findByUserAndExtractedTextContainingIgnoreCase(user, keyword)
                .forEach(document -> {

                    boolean exists = results.stream()
                            .anyMatch(r -> r.getId().equals(document.getId()));

                    if (!exists) {
                        results.add(
                                SearchResultDTO.builder()
                                        .id(document.getId())
                                        .type("DOCUMENT")
                                        .title(document.getFileName())
                                        .description("Matched inside PDF")
                                        .build()
                        );
                    }

                });

        // KNOWLEDGE GRAPH
        knowledgeNodeRepository
                .findByUserAndNameContainingIgnoreCase(user, keyword)
                .forEach(node -> results.add(
                        SearchResultDTO.builder()
                                .id(node.getId())
                                .type("KNOWLEDGE_NODE")
                                .title(node.getName())
                                .description(node.getType().name())
                                .build()
                ));

        return results;
    }
}