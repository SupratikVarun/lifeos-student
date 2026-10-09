package com.lifeos.student.controller;

import java.util.UUID;
import com.lifeos.student.dto.UpdateSubjectDTO;
import com.lifeos.student.dto.SubjectRequestDTO;
import com.lifeos.student.dto.SubjectResponseDTO;
import com.lifeos.student.service.SubjectService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // ADD SUBJECT
    @PostMapping
    public SubjectResponseDTO addSubject(
            Authentication authentication,
            @RequestBody SubjectRequestDTO request) {

        String collegeEmail = authentication.getName();

        return subjectService.addSubject(collegeEmail, request);
    }

    // GET ALL SUBJECTS
    @GetMapping
    public List<SubjectResponseDTO> getSubjects(
            Authentication authentication) {

        String collegeEmail = authentication.getName();

        return subjectService.getSubjects(collegeEmail);
    }

    // DELETE SUBJECT
    @DeleteMapping("/{id}")
    public String deleteSubject(
            Authentication authentication,
            @PathVariable String id) {

        String collegeEmail = authentication.getName();

        subjectService.deleteSubject(collegeEmail, id);

        return "Subject deleted successfully.";
    }

    @PutMapping("/{id}")
    public SubjectResponseDTO updateSubject(
            @PathVariable UUID id,
            @RequestBody UpdateSubjectDTO request,
            Authentication authentication) {

        return subjectService.updateSubject(
                id,
                request,
                authentication.getName());
    }
}