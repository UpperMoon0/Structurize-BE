package com.nhat.structurizebe.util;

import com.flowpowered.nbt.*;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.nhat.structurizebe.models.documents.StructureDocument;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTUtil {

    public static StructureDocument readStructureFromNBT(String name, String description, InputStream inputStream) {
        try (NBTInputStream nbtInputStream = new NBTInputStream(inputStream)) {

            CompoundTag rootTag = (CompoundTag) nbtInputStream.readTag();
            CompoundMap value = rootTag.getValue();

            @SuppressWarnings("unchecked")
            ListTag<Tag<Integer>> sizeTag = (ListTag<Tag<Integer>>) value.get("size");

            @SuppressWarnings("unchecked")
            ListTag<Tag<CompoundMap>> paletteTag = (ListTag<Tag<CompoundMap>>) value.get("palette");

            @SuppressWarnings("unchecked")
            ListTag<Tag<CompoundMap>> blocksTag = (ListTag<Tag<CompoundMap>>) value.get("blocks");

            if (sizeTag == null || paletteTag == null) {
                return null;
            }

            int sizeX = sizeTag.getValue().get(0).getValue();
            int sizeY = sizeTag.getValue().get(1).getValue();
            int sizeZ = sizeTag.getValue().get(2).getValue();
            if (sizeX <= 0 || sizeY <= 0 || sizeZ <= 0) {
                return null;
            }
            String[][][] blockIds = new String[sizeY][sizeZ][sizeX];

            Map<Integer, String> palette = new HashMap<>();
            for (int i = 0; i < paletteTag.getValue().size(); i++) {
                CompoundMap cm = paletteTag.getValue().get(i).getValue();
                String blockId = cm.get("Name").getValue().toString();
                palette.put(i, blockId);
            }

            for (Tag<CompoundMap> blockTag : blocksTag.getValue()) {
                CompoundMap cm = blockTag.getValue();
                int state = (int) cm.get("state").getValue();
                @SuppressWarnings("unchecked")
                List<IntTag> pos = (List<IntTag>) cm.get("pos").getValue();
                int x = pos.get(0).getValue();
                int y = pos.get(1).getValue();
                int z = pos.get(2).getValue();
                int currentY = sizeY - y - 1;
                blockIds[currentY][z][x] = palette.get(state);
            }

            return StructureDocument.builder()
                    .name(name)
                    .description(description)
                    .blockIds(blockIds)
                    .build();
        } catch (IOException e) {
            System.out.println("Error reading NBT file");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds exception");
        }
        return null;
    }
}