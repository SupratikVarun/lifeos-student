package com.lifeos.student.knowledgegraph.repository;

import com.lifeos.student.entity.User;
import com.lifeos.student.knowledgegraph.entity.KnowledgeNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KnowledgeNodeRepository
        extends JpaRepository<KnowledgeNode, UUID> {

    List<KnowledgeNode> findByUser(User user);

    Optional<KnowledgeNode> findByUserAndNameIgnoreCase(
            User user,
            String name
    );

    List<KnowledgeNode> findByUserAndNameContainingIgnoreCase(
            User user,
            String name
    );

}