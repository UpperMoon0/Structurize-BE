package com.nhat.structurizebe.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WallBlockProperties extends BlockProperties {
    public static final int WALL_NONE = 0;
    public static final int WALL_LOW = 1;
    public static final int WALL_TALL = 2;

    private int east;
    private int north;
    private int south;
    private int west;
    private int up;
    protected boolean waterlogged;

    public WallBlockProperties() {
        super(TYPE_WALL);
    }
}
