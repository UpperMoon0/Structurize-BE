package com.nhat.structurizebe.models.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "structure_likes")
public class StructureLikeDocument {
    @Id
    private String id;
    private String structureId;
    private String accountId;
    private LocalDateTime createdAt = LocalDateTime.now();
}
