package com.lifeos.student.service;

import com.lifeos.student.dto.AddNoteDTO;
import com.lifeos.student.dto.NoteResponseDTO;
import com.lifeos.student.dto.UpdateNoteDTO;
import com.lifeos.student.entity.Note;
import com.lifeos.student.entity.Subject;
import com.lifeos.student.entity.User;
import com.lifeos.student.repository.NoteRepository;
import com.lifeos.student.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final SubjectRepository subjectRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository,
                       SubjectRepository subjectRepository,
                       UserService userService) {
        this.noteRepository = noteRepository;
        this.subjectRepository = subjectRepository;
        this.userService = userService;
    }

    // ADD NOTE
    public NoteResponseDTO addNote(AddNoteDTO request, String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Note note = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .fileUrl(request.getFileUrl())
                .subject(subject)
                .user(user)
                .build();

        noteRepository.save(note);

        return mapToDTO(note);
    }

    // GET ALL NOTES
    public List<NoteResponseDTO> getAllNotes(String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        return noteRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET NOTES BY SUBJECT
    public List<NoteResponseDTO> getNotesBySubject(UUID subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        return noteRepository.findBySubject(subject)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE NOTE
    public NoteResponseDTO updateNote(UUID noteId,
                                      UpdateNoteDTO request,
                                      String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setFileUrl(request.getFileUrl());

        noteRepository.save(note);

        return mapToDTO(note);
    }

    // DELETE NOTE
    public void deleteNote(UUID noteId, String collegeEmail) {

        User user = userService.getCurrentUser(collegeEmail);

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        noteRepository.delete(note);
    }

    // DTO MAPPER
    private NoteResponseDTO mapToDTO(Note note) {

        return NoteResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .fileUrl(note.getFileUrl())
                .subjectId(note.getSubject().getId())
                .build();
    }
}