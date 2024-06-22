package com.neonlab.common.services;
import com.neonlab.common.entities.Document;
import com.neonlab.common.expectations.InvalidInputException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.repositories.DocumentRepository;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ImageKit serviceProvider;

    @Transactional
    public List<Document> saveAll(List<MultipartFile> files) throws ServerException {
        return files.stream()
                .map(file -> {
                    try{
                        return save(file);
                    } catch (ServerException e){
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public Document save(MultipartFile file) throws ServerException {
        try {
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());
            var result = uploadDocument(base64, file.getOriginalFilename());
            var document = new Document();
            document.setUrl(result.getUrl());
            document.setServiceProviderFileId(result.getFileId());
            return documentRepository.save(document);
        } catch (IOException | ForbiddenException | TooManyRequestsException | InternalServerException |
                 UnauthorizedException | BadRequestException | UnknownException e) {
            log.warn(e.getMessage());
            throw new ServerException(e.getMessage());
        }
    }

    private Result uploadDocument(String base64, String fileName) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        var uploadRequest = new FileCreateRequest(base64, fileName);
        uploadRequest.setFolder("kasera-Store");
        return serviceProvider.upload(uploadRequest);
    }

    public Document save(Document document){
        return documentRepository.save(document);
    }

    public Document fetchById(String id) throws InvalidInputException {
        return documentRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException("Document not found with id "+id));
    }

    public void delete(Document document) throws ServerException {
        try {
            serviceProvider.deleteFile(document.getServiceProviderFileId());
            documentRepository.delete(document);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public List<Document> fetchByDocIdentifierAndEntityName(String id, String entityName) {
        return documentRepository.findByDocIdentifierAndEntityNameOrderByCreatedAtDesc(id, entityName);
    }

    public void maintainSize(List<Document> documentList, Integer size) throws ServerException {
        try {
            int requiredSize = documentList.size()-size;
            for(int i = 0; i<requiredSize; i++){
                var document = documentList.get(i);
                delete(document);
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public List<Document> fetchByDocIdentifierAndEntityNameAsc(String id, String entityName) {
        return documentRepository.findByDocIdentifierAndEntityNameOrderByCreatedAtAsc(id, entityName);
    }
}
