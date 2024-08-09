package com.nhat.structurizebe.controllers;

import com.nhat.structurizebe.models.dto.request.CreateStructureCommentRequest;
import com.nhat.structurizebe.services.StructureCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RequiredArgsConstructor
@RequestMapping("/api/structure-comment")
@RestController
public class StructureCommentController {

    private final StructureCommentService structureCommentService;

    private static final Logger LOGGER = Logger.getLogger(StructureCommentController.class.getName());

    @PostMapping("/create")
    public ResponseEntity<String> createStructureComment(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                         @RequestBody CreateStructureCommentRequest request) {
        try {
            String jwt = authorizationHeader.substring(7);
            structureCommentService.createStructureComment(request.getStructureId(), request.getContent(), jwt);
            return ResponseEntity.ok("Structure comment created successfully");
        } catch (UsernameNotFoundException e) {
            LOGGER.severe("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("User not found");
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Failed to create structure comment");
        }
    }
}
