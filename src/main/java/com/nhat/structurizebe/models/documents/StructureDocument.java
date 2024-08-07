package com.nhat.structurizebe.models.documents;

import com.nhat.structurizebe.models.BlockData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@Document(collection = "structures")
public class StructureDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private BlockData[][][] blocksData;
}