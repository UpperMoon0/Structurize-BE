package com.nhat.structurizebe.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlabBlockProperties extends BlockProperties {
    public static final boolean HALF_BOTTOM = false;
    public static final boolean HALF_TOP = true;

    private boolean half;
    protected boolean waterlogged;

    public SlabBlockProperties() {
        super(TYPE_SLAB);
    }
}
