package com.nhat.structurizebe.models.documents;

import com.nhat.structurizebe.models.BlockData;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "structures")
public class StructureDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private int[][][] blocks;
    private BlockData[] palette;
    private String authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}