package com.nhat.structurizebe.controllers;

import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.services.StructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            structureService.createStructureFromNBTFile(name, description, file);
            return ResponseEntity.ok("Structure created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("delete-structure")
    public ResponseEntity<String> deleteStructure(@RequestParam String id) {
        structureService.deleteStructure(id);
        return ResponseEntity.ok("Structure deleted successfully");
    }

    @GetMapping("/download-nbt/{id}")
    public ResponseEntity<InputStreamResource> downloadNBTFile(@PathVariable String id) throws IOException {
        MultipartFile nbtFile = structureService.getNBTFileById(id);

        if (nbtFile == null) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(nbtFile.getInputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(nbtFile.getSize())
                .body(resource);
    }
}
