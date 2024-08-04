package com.nhat.structurizebe.models.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "blocks")
public class BlockDocument {
    @Id
    private String id;
    private String name;
}
