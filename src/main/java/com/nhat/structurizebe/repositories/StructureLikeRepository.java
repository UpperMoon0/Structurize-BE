package com.nhat.structurizebe.repositories;

import com.nhat.structurizebe.models.documents.StructureLikeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StructureLikeRepository extends MongoRepository<StructureLikeDocument, String>{
    Optional<StructureLikeDocument> findByAccountIdAndStructureId(String accountId, String structureId);
    List<StructureLikeDocument> findByStructureId(String structureId);
}
