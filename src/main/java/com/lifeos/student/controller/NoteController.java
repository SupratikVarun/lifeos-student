package com.lifeos.student.controller;

import com.lifeos.student.dto.AddNoteDTO;
import com.lifeos.student.dto.NoteResponseDTO;
import com.lifeos.student.dto.UpdateNoteDTO;
import com.lifeos.student.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // ADD NOTE
    @PostMapping
    public NoteResponseDTO addNote(
            @RequestBody AddNoteDTO request,
            Authentication authentication) {

        return noteService.addNote(request, authentication.getName());
    }

    // GET ALL NOTES
    @GetMapping
    public List<NoteResponseDTO> getAllNotes(Authentication authentication) {

        return noteService.getAllNotes(authentication.getName());
    }

    // GET NOTES BY SUBJECT
    @GetMapping("/subject/{subjectId}")
    public List<NoteResponseDTO> getNotesBySubject(
            @PathVariable UUID subjectId) {

        return noteService.getNotesBySubject(subjectId);
    }

    // UPDATE NOTE
    @PutMapping("/{noteId}")
    public NoteResponseDTO updateNote(
            @PathVariable UUID noteId,
            @RequestBody UpdateNoteDTO request,
            Authentication authentication) {

        return noteService.updateNote(
                noteId,
                request,
                authentication.getName()
        );
    }

    // DELETE NOTE
    @DeleteMapping("/{noteId}")
    public String deleteNote(
            @PathVariable UUID noteId,
            Authentication authentication) {

        noteService.deleteNote(noteId, authentication.getName());

        return "Note deleted successfully.";
    }
}