package com.nhat.structurizebe.repositories;

import com.nhat.structurizebe.models.documents.StructureDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StructureRepository extends MongoRepository<StructureDocument, String> {
}
