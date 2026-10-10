package com.lifeos.student.repository;

import com.lifeos.student.entity.Subject;
import com.lifeos.student.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    List<Subject> findByUser(User user);

    List<Subject> findByUserAndSubjectNameContainingIgnoreCase(
            User user,
            String subjectName
    );

}