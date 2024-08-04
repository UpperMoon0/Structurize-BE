package com.nhat.structurizebe.repositories;

import com.nhat.structurizebe.models.documents.BlockDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockRepository extends MongoRepository<BlockDocument, String> {
}
