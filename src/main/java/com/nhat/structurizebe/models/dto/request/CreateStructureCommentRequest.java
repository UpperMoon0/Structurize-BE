package com.nhat.structurizebe.models.dto.request;

import lombok.Data;

@Data
public class CreateStructureCommentRequest {
    String structureId;
    String content;
}
