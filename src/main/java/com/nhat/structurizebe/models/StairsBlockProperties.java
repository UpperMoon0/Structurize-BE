package com.nhat.structurizebe.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StairsBlockProperties extends BlockProperties {
    public static final boolean HALF_BOTTOM = false;
    public static final boolean HALF_TOP = true;

    public static final int SHAPE_STRAIGHT = 0;
    public static final int SHAPE_INNER_LEFT = 1;
    public static final int SHAPE_INNER_RIGHT = 2;
    public static final int SHAPE_OUTER_LEFT = 3;
    public static final int SHAPE_OUTER_RIGHT = 4;

    public static final int FACING_NORTH = 0;
    public static final int FACING_EAST = 1;
    public static final int FACING_SOUTH = 2;
    public static final int FACING_WEST = 3;

    private boolean half;
    private int shape;
    protected int facing;
    protected boolean waterlogged;

    public StairsBlockProperties() {
        super(TYPE_STAIRS);
    }
}
