package com.nhat.structurizebe.models.dto.response;

import com.nhat.structurizebe.exception.AccountNotFoundException;
import com.nhat.structurizebe.models.documents.AccountDocument;
import com.nhat.structurizebe.models.documents.StructureCommentDocument;
import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.repositories.AccountRepository;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class StructureDetailsResponse {
    private StructureDocument structure;
    private String authorName;
    private int likeCount;

    public StructureDetailsResponse(StructureDocument structure,
                                    String authorName,
                                    int likeCount) {
        this.structure = structure;
        this.authorName = authorName;
        this.likeCount = likeCount;
    }
}