package com.nhat.structurizebe.repositories;

import com.nhat.structurizebe.models.documents.StructureCommentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StructureCommentRepository extends MongoRepository<StructureCommentDocument, String> {
    List<StructureCommentDocument> findByStructureId(String structureId);
    int countByStructureId(String structureId);
}
