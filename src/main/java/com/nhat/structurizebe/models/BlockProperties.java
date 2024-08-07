package com.nhat.structurizebe.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockProperties {
    public static final int TYPE_BLOCK = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_SLAB = 2;
    public static final int TYPE_STAIRS = 3;
    public static final int TYPE_PRESSURE_PLATE = 4;

    protected int type;

    public BlockProperties() {
        this.type = TYPE_BLOCK;
    }

    public BlockProperties(int type) {
        this.type = type;
    }
}
