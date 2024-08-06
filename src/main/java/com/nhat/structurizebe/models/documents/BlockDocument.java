package com.nhat.structurizebe.models.documents;

import com.nhat.structurizebe.models.BlockProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Setter
@Getter
@Document(collection = "blocks")
public class BlockDocument {
    @Id
    private String id;
    private String name;
    private Map<String, String> textures;
}
