package com.nhat.structurizebe.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressurePlateBlockProperties extends BlockProperties {
    private boolean powered;

    public PressurePlateBlockProperties() {
        super(TYPE_PRESSURE_PLATE);
    }
}
