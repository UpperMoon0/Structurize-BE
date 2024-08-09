package com.nhat.structurizebe.models.dto.response;

import com.nhat.structurizebe.exception.AccountNotFoundException;
import com.nhat.structurizebe.models.documents.AccountDocument;
import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.models.documents.StructureLikeDocument;
import com.nhat.structurizebe.repositories.AccountRepository;
import com.nhat.structurizebe.repositories.StructureCommentRepository;
import com.nhat.structurizebe.repositories.StructureLikeRepository;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class StructureListResponse {
    private List<StructureListItem> structures = new ArrayList<>();

    public StructureListResponse(List<StructureDocument> structures,
                                 AccountRepository accountRepository,
                                 @Nullable String currentUserId,
                                 StructureLikeRepository structureLikeRepository,
                                 StructureCommentRepository structureCommentRepository) throws AccountNotFoundException {
        for (StructureDocument structure : structures) {
            AccountDocument author = accountRepository.findById(structure.getAuthorId()).orElseThrow(AccountNotFoundException::new);
            boolean isAuthor = author.getId().equals(currentUserId);
            StructureLikeDocument myLike = structureLikeRepository.findByAccountIdAndStructureId(currentUserId, structure.getId()).orElse(null);
            boolean isLiked = myLike != null;
            int likeCount = structureLikeRepository.countByStructureId(structure.getId());
            int commentCount = structureCommentRepository.countByStructureId(structure.getId());
            this.structures.add(new StructureListItem(structure.getId(), structure.getName(), structure.getUpdatedAt(), author.getUsername(), isAuthor, isLiked, likeCount, commentCount));
        }
    }
}

record StructureListItem(String structureId,
                         String structureName,
                         LocalDateTime updatedAt,
                         String authorName,
                         boolean isAuthor,
                         boolean isLiked,
                         int likeCount,
                         int commentCount) {
}
