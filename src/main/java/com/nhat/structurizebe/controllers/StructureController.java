package com.nhat.structurizebe.controllers;

import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.services.StructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/structure")
@RestController
public class StructureController {
    private final StructureService structureService;

    @GetMapping("get-structure")
    public ResponseEntity<StructureDocument> getStructureById(@RequestParam String id) {
        return ResponseEntity.ok(structureService.getStructureById(id));
    }

    @PostMapping("create-structure")
    public ResponseEntity<String> createStructure(@RequestBody StructureDocument structure) {
        structureService.createStructure(structure);
        return ResponseEntity.ok("Structure created successfully");
    }

    @DeleteMapping("delete-structure")
    public ResponseEntity<String> deleteStructure(@RequestParam String id) {
        structureService.deleteStructure(id);
        return ResponseEntity.ok("Structure deleted successfully");
    }
}
