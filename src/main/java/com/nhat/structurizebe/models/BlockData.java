package com.nhat.structurizebe.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlockData {
    private String id;
    private BlockProperties properties;
}
