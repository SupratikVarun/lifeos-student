package com.lifeos.student.knowledgegraph.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeos.student.ai.dto.AIRequestDTO;
import com.lifeos.student.ai.dto.AIResponseDTO;
import com.lifeos.student.ai.enums.ProviderType;
import com.lifeos.student.ai.service.AIManagerService;
import com.lifeos.student.entity.Document;
import com.lifeos.student.entity.User;
import com.lifeos.student.knowledgegraph.dto.KnowledgeGraphDTO;
import com.lifeos.student.knowledgegraph.dto.KnowledgeNodeDTO;
import com.lifeos.student.knowledgegraph.dto.KnowledgeRelationDTO;
import com.lifeos.student.knowledgegraph.entity.KnowledgeNode;
import com.lifeos.student.knowledgegraph.entity.KnowledgeRelation;
import com.lifeos.student.knowledgegraph.repository.KnowledgeNodeRepository;
import com.lifeos.student.knowledgegraph.repository.KnowledgeRelationRepository;
import com.lifeos.student.repository.DocumentRepository;
import com.lifeos.student.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KnowledgeGraphServiceImpl implements KnowledgeGraphService {

    private final DocumentRepository documentRepository;

    private final UserRepository userRepository;

    private final KnowledgeNodeRepository knowledgeNodeRepository;

    private final KnowledgeRelationRepository knowledgeRelationRepository;

    private final AIManagerService aiManagerService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void generateKnowledgeGraph(UUID documentId, String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        AIRequestDTO request = new AIRequestDTO();
        request.setProvider(ProviderType.AUTO);
        request.setText(document.getExtractedText());

        AIResponseDTO response =
                aiManagerService.generateKnowledgeGraph(request);

        KnowledgeGraphDTO graph;

        try {

            graph = objectMapper.readValue(
                    response.getResponse().trim(),
                    KnowledgeGraphDTO.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse Knowledge Graph JSON",
                    e
            );

        }

        Map<String, KnowledgeNode> nodeMap = new HashMap<>();

        for (KnowledgeNodeDTO nodeDTO : graph.getNodes()) {

            KnowledgeNode node = knowledgeNodeRepository
                    .findByUserAndNameIgnoreCase(
                            user,
                            nodeDTO.getName()
                    )
                    .orElseGet(() -> {

                        KnowledgeNode newNode = KnowledgeNode.builder()
                                .name(nodeDTO.getName())
                                .type(nodeDTO.getType())
                                .user(user)
                                .build();

                        return knowledgeNodeRepository.save(newNode);

                    });

            nodeMap.put(
                    node.getName().toLowerCase(),
                    node
            );

        }

        for (KnowledgeRelationDTO relationDTO : graph.getRelations()) {

            KnowledgeNode source =
                    nodeMap.get(
                            relationDTO.getSource().toLowerCase()
                    );

            KnowledgeNode target =
                    nodeMap.get(
                            relationDTO.getTarget().toLowerCase()
                    );

            if (source == null || target == null) {
                continue;
            }

            boolean exists =
                    knowledgeRelationRepository
                            .existsBySourceNodeAndTargetNodeAndRelationType(
                                    source,
                                    target,
                                    relationDTO.getRelation()
                            );

            if (!exists) {

                KnowledgeRelation relation =
                        KnowledgeRelation.builder()
                                .sourceNode(source)
                                .targetNode(target)
                                .relationType(relationDTO.getRelation())
                                .build();

                knowledgeRelationRepository.save(relation);

            }

        }

    }

    @Override
    public KnowledgeGraphDTO getKnowledgeGraph(String email) {

        User user = userRepository.findByCollegeEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        KnowledgeGraphDTO dto = new KnowledgeGraphDTO();

        dto.setNodes(
                knowledgeNodeRepository.findByUser(user)
                        .stream()
                        .map(node -> {

                            KnowledgeNodeDTO n = new KnowledgeNodeDTO();
                            n.setName(node.getName());
                            n.setType(node.getType());

                            return n;

                        })
                        .toList()
        );

        dto.setRelations(
                knowledgeRelationRepository.findAll()
                        .stream()
                        .map(relation -> {

                            KnowledgeRelationDTO r = new KnowledgeRelationDTO();
                            r.setSource(relation.getSourceNode().getName());
                            r.setTarget(relation.getTargetNode().getName());
                            r.setRelation(relation.getRelationType());

                            return r;

                        })
                        .toList()
        );

        return dto;
    }

}