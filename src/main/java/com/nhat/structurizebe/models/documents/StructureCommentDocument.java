package com.nhat.structurizebe.models.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "structure_comments")
public class StructureCommentDocument {
    @Id
    private String id;
    private String structureId;
    private String accountId;
    private String content;
    private long createdAt;
    private long updatedAt;
}
