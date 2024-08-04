package com.nhat.structurizebe.services;

import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.repositories.StructureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StructureService {
    private final StructureRepository structureRepository;

    public StructureDocument getStructureById(String id) {
        return structureRepository.findById(id).orElse(null);
    }

    public void createStructure(StructureDocument structure) {
        structureRepository.save(structure);
    }

    public void deleteStructure(String id) {
        structureRepository.deleteById(id);
    }
}
