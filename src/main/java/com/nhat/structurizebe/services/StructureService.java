package com.nhat.structurizebe.services;

import com.nhat.structurizebe.exception.AccountNotFoundException;
import com.nhat.structurizebe.models.documents.AccountDocument;
import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.models.dto.response.CommonMultipartFile;
import com.nhat.structurizebe.models.dto.response.StructureListResponse;
import com.nhat.structurizebe.repositories.AccountRepository;
import com.nhat.structurizebe.repositories.StructureCommentRepository;
import com.nhat.structurizebe.repositories.StructureLikeRepository;
import com.nhat.structurizebe.repositories.StructureRepository;
import com.nhat.structurizebe.util.NbtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StructureService {

    private final StructureRepository structureRepository;
    private final AccountRepository accountRepository;
    private final StructureLikeRepository structureLikeRepository;
    private final StructureCommentRepository structureCommentRepository;
    private final AuthService authService;

    public StructureDocument getStructureById(String id) {
        return structureRepository.findById(id).orElse(null);
    }

    public List<StructureDocument> getAllStructures() {
        return structureRepository.findAll();
    }

    public StructureListResponse getStructureList(String jwt) throws AccountNotFoundException {
        AccountDocument account = authService.getAccountByJwt(jwt);
        return new StructureListResponse(structureRepository.findAll(), accountRepository, account.getId(), structureLikeRepository, structureCommentRepository);
    }

    public StructureListResponse getStructureList() throws AccountNotFoundException {
        return new StructureListResponse(structureRepository.findAll(), accountRepository, null, structureLikeRepository, structureCommentRepository);
    }

    public void createStructureFromNBTFile(String name, String description, String authorId, MultipartFile file) {
        try {
            StructureDocument structure = NbtUtil.readStructureFromNBT(name, description, file.getInputStream());

            if (structure == null) {
                return;
            }

            structure.setAuthorId(authorId);
            structure.setCreatedAt(LocalDateTime.now());
            structure.setUpdatedAt(LocalDateTime.now());

            structureRepository.save(structure);
        } catch (IOException e) {
            System.out.println("Error processing NBT file");
        }
    }

    public MultipartFile getNBTFileById(String id) {
        StructureDocument structure = structureRepository.findById(id).orElse(null);

        if (structure == null) {
            return null;
        }

        ByteArrayOutputStream outputStream = NbtUtil.writeStructureToNBT(structure);
        if (outputStream == null) {
            return null;
        }

        byte[] byteArray = outputStream.toByteArray();
        return new CommonMultipartFile(byteArray, ".nbt");
    }

    public void deleteStructure(String id) {
        structureRepository.deleteById(id);
    }
}
