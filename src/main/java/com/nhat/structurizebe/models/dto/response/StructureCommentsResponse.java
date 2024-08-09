package com.nhat.structurizebe.models.dto.response;

import com.nhat.structurizebe.exception.AccountNotFoundException;
import com.nhat.structurizebe.models.documents.AccountDocument;
import com.nhat.structurizebe.models.documents.StructureCommentDocument;
import com.nhat.structurizebe.repositories.AccountRepository;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class StructureCommentsResponse {
    List<StructureComment> comments = new ArrayList<>();

    public StructureCommentsResponse(List<StructureCommentDocument> comments, AccountRepository accountRepository) {
        for (StructureCommentDocument comment : comments) {
            AccountDocument account = accountRepository.findById(comment.getAccountId()).orElseThrow(AccountNotFoundException::new);
            this.comments.add(new StructureComment(
                account.getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
            ));
        }

    }
}

record StructureComment (
    String authorName,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
