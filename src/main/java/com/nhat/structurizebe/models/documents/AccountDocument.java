package com.nhat.structurizebe.models.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "accounts")
public class AccountDocument {
    @Id
    private String id;
    private String email;
    private String username;
    private String password;
    private Set<RoleDocument> roles = new HashSet<>();
}