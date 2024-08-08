package com.nhat.structurizebe.repositories;

import com.nhat.structurizebe.models.documents.RoleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<RoleDocument, String> {
    Optional<RoleDocument> findByName(String name);
}
