package com.nhat.structurizebe.controllers;

import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.services.StructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/structure")
@RestController
public class StructureController {
    private final StructureService structureService;

    @GetMapping("get-structure")
    public ResponseEntity<StructureDocument> getStructureById(@RequestParam String id) {
        return ResponseEntity.ok(structureService.getStructureById(id));
    }

    @GetMapping("get-all-structures")
    public ResponseEntity<Iterable<StructureDocument>> getAllStructures() {
        return ResponseEntity.ok(structureService.getAllStructures());
    }

    @PostMapping("/create-structure-from-nbt")
    public ResponseEntity<String> createStructureFromNBT(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestPart("file") MultipartFile file) {
        try {
            structureService.createStructureFromNBT(name, description, file);
            return ResponseEntity.ok("Structure created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("delete-structure")
    public ResponseEntity<String> deleteStructure(@RequestParam String id) {
        structureService.deleteStructure(id);
        return ResponseEntity.ok("Structure deleted successfully");
    }
}
