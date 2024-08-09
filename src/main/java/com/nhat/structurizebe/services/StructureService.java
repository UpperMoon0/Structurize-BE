package com.nhat.structurizebe.services;

import com.nhat.structurizebe.exception.AccountNotFoundException;
import com.nhat.structurizebe.exception.StructureNotFoundException;
import com.nhat.structurizebe.models.documents.AccountDocument;
import com.nhat.structurizebe.models.documents.StructureCommentDocument;
import com.nhat.structurizebe.models.documents.StructureDocument;
import com.nhat.structurizebe.models.dto.response.CommonMultipartFile;
import com.nhat.structurizebe.models.dto.response.StructureDetailsResponse;
import com.nhat.structurizebe.models.dto.response.StructureListResponse;
import com.nhat.structurizebe.repositories.AccountRepository;
import com.nhat.structurizebe.repositories.StructureCommentRepository;
import com.nhat.structurizebe.repositories.StructureLikeRepository;
import com.nhat.structurizebe.repositories.StructureRepository;
import com.nhat.structurizebe.util.NbtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StructureService {

    private final StructureRepository structureRepository;
    private final AccountRepository accountRepository;
    private final StructureLikeRepository structureLikeRepository;
    private final StructureCommentRepository structureCommentRepository;
    private final AuthService authService;

    public StructureDocument getStructureById(String id) throws StructureNotFoundException {
        return structureRepository.findById(id).orElseThrow(StructureNotFoundException::new);
    }

    public StructureDetailsResponse getStructureDetails(String id) throws StructureNotFoundException, AccountNotFoundException {
        StructureDocument structure = structureRepository.findById(id).orElseThrow(StructureNotFoundException::new);
        AccountDocument account = accountRepository.findById(structure.getAuthorId()).orElseThrow(AccountNotFoundException::new);
        int likeCount = structureLikeRepository.countByStructureId(id);

        return new StructureDetailsResponse(structure, account.getUsername(), likeCount);
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

    public void uploadAsNBTFile(String name, String description, String authorId, MultipartFile file) {
        try {
            StructureDocument structure = NbtUtil.readStructureFromNBT(name, description, file.getInputStream());

            if (structure == null) {
                return;
            }

            structure.setAuthorId(authorId);

            structureRepository.save(structure);
        } catch (IOException e) {
            System.out.println("Error processing NBT file");
        }
    }

    public MultipartFile downloadAsNBTFile(String id) throws RuntimeException {
        StructureDocument structure = structureRepository.findById(id).orElseThrow(StructureNotFoundException::new);

        ByteArrayOutputStream outputStream = NbtUtil.writeStructureToNBT(structure);
        if (outputStream == null) {
            throw new RuntimeException("Error writing NBT file");
        }

        structure.setDownload(structure.getDownload() + 1);
        structureRepository.save(structure);

        byte[] byteArray = outputStream.toByteArray();
        return new CommonMultipartFile(byteArray, ".nbt");
    }

    public void deleteStructure(String id) {
        structureRepository.deleteById(id);
    }
}
