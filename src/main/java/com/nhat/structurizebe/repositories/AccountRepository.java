package com.nhat.structurizebe.repositories;

import com.nhat.structurizebe.models.documents.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<AccountDocument, String> {
    Optional<AccountDocument> findByEmail(String email);
}