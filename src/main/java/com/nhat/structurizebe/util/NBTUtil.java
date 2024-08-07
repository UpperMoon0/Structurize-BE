package com.nhat.structurizebe.util;

import com.flowpowered.nbt.*;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.nhat.structurizebe.models.*;
import com.nhat.structurizebe.models.documents.StructureDocument;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class NBTUtil {
    /**
     * Reads a structure from an NBT input stream and constructs a StructureDocument.
     *
     * @param name        the name of the structure
     * @param description the description of the structure
     * @param inputStream the input stream containing the NBT data
     * @return a StructureDocument representing the structure, or null if an error occurs
     */
    @SuppressWarnings("unchecked")
    public static StructureDocument readStructureFromNBT(String name, String description, InputStream inputStream) {
        try (NBTInputStream nbtInputStream = new NBTInputStream(inputStream)) {

            CompoundTag rootTag = (CompoundTag) nbtInputStream.readTag();
            CompoundMap value = rootTag.getValue();

            ListTag<Tag<Integer>> sizeTag = (ListTag<Tag<Integer>>) value.get("size");
            ListTag<Tag<CompoundMap>> paletteTag = (ListTag<Tag<CompoundMap>>) value.get("palette");
            ListTag<Tag<CompoundMap>> blocksTag = (ListTag<Tag<CompoundMap>>) value.get("blocks");

            if (sizeTag == null || paletteTag == null || blocksTag == null) {
                return null;
            }

            int sizeX = sizeTag.getValue().get(0).getValue();
            int sizeY = sizeTag.getValue().get(1).getValue();
            int sizeZ = sizeTag.getValue().get(2).getValue();
            if (sizeX <= 0 || sizeY <= 0 || sizeZ <= 0) {
                return null;
            }

            BlockData[] palette = new BlockData[paletteTag.getValue().size()];
            for (int i = 0; i < paletteTag.getValue().size(); i++) {
                CompoundMap paletteCM = paletteTag.getValue().get(i).getValue();

                BlockData blockData = BlockData.builder()
                        .id(paletteCM.get("Name").getValue().toString())
                        .properties(new BlockProperties())
                        .build();

                Tag<CompoundMap> propertiesTag = (Tag<CompoundMap>) paletteCM.get("Properties");
                if (propertiesTag != null) {
                    CompoundMap propertiesCM = propertiesTag.getValue();

                    Tag<String> shapeTag = (Tag<String>) propertiesCM.get("shape");
                    if (shapeTag != null) {
                        final int shape = getShape(shapeTag);
                        final int facing = getFacing((Tag<String>) propertiesCM.get("facing"));
                        final boolean half = ((Tag<String>) propertiesCM.get("half")).getValue().equals("top");
                        final boolean waterlogged = ((Tag<String>) propertiesCM.get("waterlogged")).getValue().equals("true");

                        StairsBlockProperties properties = new StairsBlockProperties();
                        properties.setShape(shape);
                        properties.setFacing(facing);
                        properties.setHalf(half);
                        properties.setWaterlogged(waterlogged);
                        blockData.setProperties(properties);
                    }

                    Tag<String> slabTypeTag = (Tag<String>) propertiesCM.get("type");
                    if (slabTypeTag != null) {
                        final boolean half = slabTypeTag.getValue().equals("top") ? SlabBlockProperties.HALF_TOP : SlabBlockProperties.HALF_BOTTOM;
                        final boolean waterlogged = ((Tag<String>) propertiesCM.get("waterlogged")).getValue().equals("true");
                        SlabBlockProperties properties = new SlabBlockProperties();
                        properties.setHalf(half);
                        properties.setWaterlogged(waterlogged);
                        blockData.setProperties(properties);
                    }

                    Tag<String> poweredTag = (Tag<String>) propertiesCM.get("powered");
                    if (poweredTag != null) {
                        final boolean powered = poweredTag.getValue().equals("true");
                        PressurePlateBlockProperties properties = new PressurePlateBlockProperties();
                        properties.setPowered(powered);
                        blockData.setProperties(properties);
                    }

                    Tag<String> northTag = (Tag<String>) propertiesCM.get("north");
                    if (northTag != null) {
                        final int north = getWallExtendHeight(northTag);
                        final int east = getWallExtendHeight((Tag<String>) propertiesCM.get("east"));
                        final int south = getWallExtendHeight((Tag<String>) propertiesCM.get("south"));
                        final int west = getWallExtendHeight((Tag<String>) propertiesCM.get("west"));
                        final int up = getWallExtendHeight((Tag<String>) propertiesCM.get("up"));
                        final boolean waterlogged = ((Tag<String>) propertiesCM.get("waterlogged")).getValue().equals("true");
                        WallBlockProperties properties = new WallBlockProperties();
                        properties.setNorth(north);
                        properties.setEast(east);
                        properties.setSouth(south);
                        properties.setWest(west);
                        properties.setUp(up);
                        properties.setWaterlogged(waterlogged);
                        blockData.setProperties(properties);
                    }
                }

                palette[i] = blockData;
            }

            int[][][] blocks = new int[sizeY][sizeZ][sizeX];
            for (Tag<CompoundMap> blockTag : blocksTag.getValue()) {
                CompoundMap cm = blockTag.getValue();
                int state = (int) cm.get("state").getValue();
                List<IntTag> pos = (List<IntTag>) cm.get("pos").getValue();
                int x = pos.get(0).getValue();
                int y = pos.get(1).getValue();
                int z = pos.get(2).getValue();
                int currentY = sizeY - y - 1;
                blocks[currentY][z][x] = state;
            }

            return StructureDocument.builder()
                    .name(name)
                    .description(description)
                    .blocks(blocks)
                    .palette(palette)
                    .build();
        } catch (IOException e) {
            System.out.println("Error reading NBT file");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds exception");
        }
        return null;
    }

    private static int getShape(Tag<String> shapeTag) {
        String shapeString = shapeTag.getValue();
        int shape;
        switch (shapeString) {
            case "inner_left" -> shape = StairsBlockProperties.SHAPE_INNER_LEFT;
            case "inner_right" -> shape = StairsBlockProperties.SHAPE_INNER_RIGHT;
            case "outer_left" -> shape = StairsBlockProperties.SHAPE_OUTER_LEFT;
            case "outer_right" -> shape = StairsBlockProperties.SHAPE_OUTER_RIGHT;
            default -> shape = StairsBlockProperties.SHAPE_STRAIGHT;
        }
        return shape;
    }

    private static int getFacing(Tag<String> facingTag) {
        String facingString = facingTag.getValue();
        int facing;
        switch (facingString) {
            case "east" -> facing = StairsBlockProperties.FACING_EAST;
            case "south" -> facing = StairsBlockProperties.FACING_SOUTH;
            case "west" -> facing = StairsBlockProperties.FACING_WEST;
            default -> facing = StairsBlockProperties.FACING_NORTH;
        }
        return facing;
    }

    private static int getWallExtendHeight(Tag<String> facingTag) {
        String facingString = facingTag.getValue();
        int wallExtendHeight;
        switch (facingString) {
            case "low" -> wallExtendHeight = WallBlockProperties.WALL_LOW;
            case "tall" -> wallExtendHeight = WallBlockProperties.WALL_TALL;
            default -> wallExtendHeight = WallBlockProperties.WALL_NONE;
        }
        return wallExtendHeight;
    }
}