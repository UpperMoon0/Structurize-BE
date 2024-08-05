package com.nhat.structurizebe.services;

import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.repositories.StructureRepository;
import com.nhat.structurizebe.util.NBTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class StructureService {
    private final StructureRepository structureRepository;

    public StructureDocument getStructureById(String id) {
        return structureRepository.findById(id).orElse(null);
    }

    public void createStructureFromNBT(String name, String description, MultipartFile file) {
        try {
            StructureDocument structure = NBTUtil.readStructureFromNBT(name, description, file.getInputStream());

            if (structure == null) {
                return;
            }

            structureRepository.save(structure);
        } catch (IOException e) {
            System.out.println("Error processing NBT file");
        }
    }

    public void deleteStructure(String id) {
        structureRepository.deleteById(id);
    }
}
