package com.lifeos.student.service;

import java.util.UUID;
import com.lifeos.student.dto.UpdateSubjectDTO;
import com.lifeos.student.dto.SubjectRequestDTO;
import com.lifeos.student.dto.SubjectResponseDTO;
import com.lifeos.student.entity.Subject;
import com.lifeos.student.entity.User;
import com.lifeos.student.repository.SubjectRepository;
import com.lifeos.student.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SubjectService(SubjectRepository subjectRepository,
                          UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    // ADD SUBJECT
    public SubjectResponseDTO addSubject(String collegeEmail,
                                         SubjectRequestDTO request) {

        User user = userRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = Subject.builder()
                .subjectName(request.getSubjectName())
                .subjectCode(request.getSubjectCode())
                .facultyName(request.getFacultyName())
                .semester(request.getSemester())
                .user(user)
                .build();

        subjectRepository.save(subject);

        return mapToDTO(subject);
    }

    // GET ALL SUBJECTS
    public List<SubjectResponseDTO> getSubjects(String collegeEmail) {

        User user = userRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return subjectRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // DELETE SUBJECT
    public void deleteSubject(String collegeEmail, String subjectId) {

        User user = userRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = subjectRepository.findById(java.util.UUID.fromString(subjectId))
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!subject.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        subjectRepository.delete(subject);
    }

    // MAPPER
    private SubjectResponseDTO mapToDTO(Subject subject) {

        return SubjectResponseDTO.builder()
                .id(subject.getId())
                .subjectName(subject.getSubjectName())
                .subjectCode(subject.getSubjectCode())
                .facultyName(subject.getFacultyName())
                .semester(subject.getSemester())
                .build();
    }

    public SubjectResponseDTO updateSubject(
            UUID id,
            UpdateSubjectDTO request,
            String collegeEmail) {

        User user = userRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!subject.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectCode(request.getSubjectCode());
        subject.setFacultyName(request.getFacultyName());
        subject.setSemester(request.getSemester());

        subjectRepository.save(subject);

        return SubjectResponseDTO.builder()
                .id(subject.getId())
                .subjectName(subject.getSubjectName())
                .subjectCode(subject.getSubjectCode())
                .facultyName(subject.getFacultyName())
                .semester(subject.getSemester())
                .build();
    }
}