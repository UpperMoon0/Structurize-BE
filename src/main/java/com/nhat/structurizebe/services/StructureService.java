package com.nhat.structurizebe.services;

import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.repositories.StructureRepository;
import com.nhat.structurizebe.util.NBTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StructureService {
    private final StructureRepository structureRepository;

    public StructureDocument getStructureById(String id) {
        return structureRepository.findById(id).orElse(null);
    }

    public void createStructureFromNBT() {
        String temp_path = "D:\\Games\\Minecraft\\TLauncher MOdpacks\\saves\\Test\\generated\\minecraft\\structures\\acacia_tree.nbt";
        StructureDocument structure = NBTUtil.readStructureFromNBT(temp_path);

        if (structure == null) {
            return;
        }

        structureRepository.save(structure);
    }

    public void deleteStructure(String id) {
        structureRepository.deleteById(id);
    }
}
