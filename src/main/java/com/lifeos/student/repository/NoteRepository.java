package com.lifeos.student.repository;

import com.lifeos.student.entity.Note;
import com.lifeos.student.entity.Subject;
import com.lifeos.student.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    List<Note> findByUser(User user);

    List<Note> findBySubject(Subject subject);
}