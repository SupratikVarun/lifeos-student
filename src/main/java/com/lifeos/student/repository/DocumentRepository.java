package com.lifeos.student.repository;

import com.lifeos.student.entity.Document;
import com.lifeos.student.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    List<Document> findByUser(User user);

    Optional<Document> findByIdAndUser(UUID id, User user);
}