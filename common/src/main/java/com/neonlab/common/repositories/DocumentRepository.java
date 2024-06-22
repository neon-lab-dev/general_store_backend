package com.neonlab.common.repositories;

import com.neonlab.common.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    List<Document> findByDocIdentifierAndEntityNameOrderByCreatedAtDesc(String id, String entityName);

    List<Document> findByDocIdentifierAndEntityNameOrderByCreatedAtAsc(String id, String entityName);
}
