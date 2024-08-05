package com.nhat.structurizebe.controllers;

import com.nhat.structurizebe.models.documents.BlockDocument;
import com.nhat.structurizebe.services.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/block")
@RestController
public class BlockController {
    private final BlockService blockService;

    @GetMapping("get-block")
    public ResponseEntity<BlockDocument> getBlockById(@RequestParam String id) {
        return ResponseEntity.ok(blockService.getBlockById(id));
    }

    @PostMapping("create-block")
    public ResponseEntity<String> createBlock(@RequestBody BlockDocument blockDocument) {
        blockService.createBlock(blockDocument);
        return ResponseEntity.ok("Block created successfully");
    }

    @DeleteMapping("delete-block")
    public ResponseEntity<String> deleteBlock(@RequestParam String id) {
        blockService.deleteBlock(id);
        return ResponseEntity.ok("Block deleted successfully");
    }

    @GetMapping("get-blocks")
    public ResponseEntity<List<BlockDocument>> getBlocks() {
        return ResponseEntity.ok(blockService.getBlocks());
    }
}
