package com.nhat.structurizebe.util;

import com.flowpowered.nbt.*;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;
import com.nhat.structurizebe.models.*;
import com.nhat.structurizebe.models.documents.StructureDocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NbtUtil {
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
            CompoundMap rootCM = rootTag.getValue();

            ListTag<Tag<Integer>> sizeTag = (ListTag<Tag<Integer>>) rootCM.get("size");
            ListTag<Tag<CompoundMap>> paletteTag = (ListTag<Tag<CompoundMap>>) rootCM.get("palette");
            ListTag<Tag<CompoundMap>> blocksTag = (ListTag<Tag<CompoundMap>>) rootCM.get("blocks");

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
                        final int shape = getShapeStringFromTag(shapeTag);
                        final int facing = getFacingStringFromTag((Tag<String>) propertiesCM.get("facing"));
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
                        final int north = getWallExtHeightStringFromTag(northTag);
                        final int east = getWallExtHeightStringFromTag((Tag<String>) propertiesCM.get("east"));
                        final int south = getWallExtHeightStringFromTag((Tag<String>) propertiesCM.get("south"));
                        final int west = getWallExtHeightStringFromTag((Tag<String>) propertiesCM.get("west"));
                        final int up = getWallExtHeightStringFromTag((Tag<String>) propertiesCM.get("up"));
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

            StructureDocument structure = new StructureDocument();
            structure.setName(name);
            structure.setDescription(description);
            structure.setBlocks(blocks);
            structure.setPalette(palette);

            return structure;
        } catch (IOException e) {
            System.out.println("Error reading NBT file");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds exception");
        }
        return null;
    }

    private static int getShapeStringFromTag(Tag<String> shapeTag) {
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

    private static int getFacingStringFromTag(Tag<String> facingTag) {
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

    private static int getWallExtHeightStringFromTag(Tag<String> facingTag) {
        String facingString = facingTag.getValue();
        int wallExtendHeight;
        switch (facingString) {
            case "low" -> wallExtendHeight = WallBlockProperties.WALL_LOW;
            case "tall" -> wallExtendHeight = WallBlockProperties.WALL_TALL;
            default -> wallExtendHeight = WallBlockProperties.WALL_NONE;
        }
        return wallExtendHeight;
    }

    public static ByteArrayOutputStream writeStructureToNBT(StructureDocument structure) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            NBTOutputStream nbtOutputStream = new NBTOutputStream(byteArrayOutputStream)) {

            CompoundTag rootTag = new CompoundTag("", new CompoundMap());

            // Blocks tag
            List<CompoundTag> blockList = new ArrayList<>();
            int[][][] blocks = structure.getBlocks();
            for (int y = 0; y < structure.getBlocks().length; y++) {
                for (int z = 0; z < structure.getBlocks()[y].length; z++) {
                    for (int x = 0; x < structure.getBlocks()[y][z].length; x++) {
                        int state = blocks[y][z][x];

                        CompoundMap blockCM = new CompoundMap();
                        blockCM.put("state", new IntTag("state", state));
                        List<IntTag> posList = List.of(new IntTag("", x), new IntTag("", blocks.length - y - 1), new IntTag("", z));
                        ListTag<IntTag> pos = new ListTag<>("pos", IntTag.class, posList);
                        blockCM.put(pos);

                        blockList.add(new CompoundTag("", blockCM));
                    }
                }
            }
            ListTag<CompoundTag> blocksTag = new ListTag<>("blocks", CompoundTag.class, blockList);
            rootTag.getValue().put(blocksTag);

            // Entities tag
            List<CompoundTag> entityList = new ArrayList<>();
            ListTag<CompoundTag> entitiesList = new ListTag<>("entities", CompoundTag.class, entityList);
            rootTag.getValue().put(entitiesList);

            // Palette tag
            List<CompoundTag> paletteList = new ArrayList<>();
            BlockData[] palette = structure.getPalette();
            for (BlockData blockData : palette) {
                CompoundMap paletteCM = new CompoundMap();
                paletteCM.put(new StringTag("Name", blockData.getId()));

                CompoundMap propertiesCM = new CompoundMap();
                if (blockData.getProperties() instanceof PressurePlateBlockProperties properties) {
                    propertiesCM.put(new StringTag("powered", properties.isPowered() ? "true" : "false"));
                    paletteCM.put(new CompoundTag("Properties", propertiesCM));
                } else if (blockData.getProperties() instanceof SlabBlockProperties properties) {
                    propertiesCM.put(new StringTag("type", properties.isHalf() ? "top" : "bottom"));
                    propertiesCM.put(new StringTag("waterlogged", properties.isWaterlogged() ? "true" : "false"));
                    paletteCM.put(new CompoundTag("Properties", propertiesCM));
                } else if (blockData.getProperties() instanceof StairsBlockProperties properties) {
                    propertiesCM.put(getShapeTagFromString(properties.getShape()));
                    propertiesCM.put(getFacingTagFromString(properties.getFacing()));
                    propertiesCM.put(new StringTag("half", properties.isHalf() ? "top" : "bottom"));
                    propertiesCM.put(new StringTag("waterlogged", properties.isWaterlogged() ? "true" : "false"));
                    paletteCM.put(new CompoundTag("Properties", propertiesCM));
                } else if (blockData.getProperties() instanceof WallBlockProperties properties) {
                    propertiesCM.put(getWallExtHeightTagFromString(properties.getNorth(), "north"));
                    propertiesCM.put(getWallExtHeightTagFromString(properties.getEast(), "east"));
                    propertiesCM.put(getWallExtHeightTagFromString(properties.getSouth(), "south"));
                    propertiesCM.put(getWallExtHeightTagFromString(properties.getWest(), "west"));
                    propertiesCM.put(getWallExtHeightTagFromString(properties.getUp(), "up"));
                    propertiesCM.put(new StringTag("waterlogged", properties.isWaterlogged() ? "true" : "false"));
                    paletteCM.put(new CompoundTag("Properties", propertiesCM));
                }

                paletteList.add(new CompoundTag("", paletteCM));
            }
            ListTag<CompoundTag> paletteTag = new ListTag<>("palette", CompoundTag.class, paletteList);
            rootTag.getValue().put(paletteTag);

            // Size tag
            ListTag<IntTag> sizeTag = new ListTag<>("size", IntTag.class,
                    List.of(new IntTag("", blocks[0][0].length), new IntTag("", blocks.length), new IntTag("", blocks[0].length)));
            rootTag.getValue().put(sizeTag);

            // DataVersion tag
            rootTag.getValue().put(new IntTag("DataVersion", 3465));

            nbtOutputStream.writeTag(rootTag);
            return byteArrayOutputStream;
        } catch (IOException e) {
            System.out.println("Error writing NBT file");
            return null;
        }
    }

    private static Tag<String> getShapeTagFromString(int shape) {
        String shapeString;
        switch (shape) {
            case StairsBlockProperties.SHAPE_INNER_LEFT -> shapeString = "inner_left";
            case StairsBlockProperties.SHAPE_INNER_RIGHT -> shapeString = "inner_right";
            case StairsBlockProperties.SHAPE_OUTER_LEFT -> shapeString = "outer_left";
            case StairsBlockProperties.SHAPE_OUTER_RIGHT -> shapeString = "outer_right";
            default -> shapeString = "straight";
        }
        return new StringTag("shape", shapeString);
    }

    private static Tag<String> getFacingTagFromString(int facing) {
        String facingString;
        switch (facing) {
            case StairsBlockProperties.FACING_EAST -> facingString = "east";
            case StairsBlockProperties.FACING_SOUTH -> facingString = "south";
            case StairsBlockProperties.FACING_WEST -> facingString = "west";
            default -> facingString = "north";
        }
        return new StringTag("facing", facingString);
    }

    private static Tag<String> getWallExtHeightTagFromString(int wallExtHeight, String direction) {
        String wallExtHeightString;
        switch (wallExtHeight) {
            case WallBlockProperties.WALL_LOW -> wallExtHeightString = "low";
            case WallBlockProperties.WALL_TALL -> wallExtHeightString = "tall";
            default -> wallExtHeightString = "none";
        }
        return new StringTag(direction, wallExtHeightString);
    }
}