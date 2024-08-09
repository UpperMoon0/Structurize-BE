package com.nhat.structurizebe.controllers;

import com.nhat.structurizebe.exception.AccountNotFoundException;
import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.models.dto.response.StructureListResponse;
import com.nhat.structurizebe.services.AuthService;
import com.nhat.structurizebe.services.JwtService;
import com.nhat.structurizebe.services.StructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RequestMapping("/api/structure")
@RestController
public class StructureController {

    private static final Logger LOGGER = Logger.getLogger(StructureController.class.getName());

    private final StructureService structureService;
    private final AuthService authService;

    @GetMapping("get-structure")
    public ResponseEntity<StructureDocument> getStructureById(@RequestParam String id) {
        return ResponseEntity.ok(structureService.getStructureById(id));
    }

    @GetMapping("get-all-structures")
    public ResponseEntity<Iterable<StructureDocument>> getAllStructures() {
        return ResponseEntity.ok(structureService.getAllStructures());
    }

    @GetMapping("get-structure-list")
    public ResponseEntity<StructureListResponse> getStructureList(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            StructureListResponse response;
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response = structureService.getStructureList();
            } else {
                String token = authorizationHeader.substring(7);
                response = structureService.getStructureList(token);
            }
            return ResponseEntity.ok(response);
        } catch (AccountNotFoundException e) {
            LOGGER.severe("Account not found");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create-structure-from-nbt")
    public ResponseEntity<String> createStructureFromNBT(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            String authorId = authService.getAccountByJwt(token).getId();

            structureService.createStructureFromNBTFile(name, description, authorId, file);
            return ResponseEntity.ok("Structure created successfully");
        } catch (Exception e) {
            LOGGER.severe("Error creating structure: " + e.getMessage());
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
