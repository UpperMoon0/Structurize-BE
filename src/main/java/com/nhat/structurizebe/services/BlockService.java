package com.nhat.structurizebe.services;

import com.nhat.structurizebe.models.documents.BlockDocument;
import com.nhat.structurizebe.repositories.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlockService {
    private final BlockRepository blockRepository;

    public BlockDocument getBlockById(String id) {
        return blockRepository.findById(id).orElse(null);
    }

    public void createBlock(BlockDocument blockDocument) {
        blockRepository.save(blockDocument);
    }

    public void deleteBlock(String id) {
        blockRepository.deleteById(id);
    }

    public List<BlockDocument> getBlocks() {
        return blockRepository.findAll();
    }
}
