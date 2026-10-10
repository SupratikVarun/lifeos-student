package com.lifeos.student.knowledgegraph.repository;

import com.lifeos.student.knowledgegraph.entity.KnowledgeNode;
import com.lifeos.student.knowledgegraph.entity.KnowledgeRelation;
import com.lifeos.student.knowledgegraph.enums.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface KnowledgeRelationRepository
        extends JpaRepository<KnowledgeRelation, UUID> {

    List<KnowledgeRelation> findBySourceNode(KnowledgeNode sourceNode);

    boolean existsBySourceNodeAndTargetNodeAndRelationType(
            KnowledgeNode sourceNode,
            KnowledgeNode targetNode,
            RelationType relationType
    );

}