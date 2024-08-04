package com.nhat.structurizebe.models.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "structures")
public class StructureDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private BlockDocument[][][] blocks;
}