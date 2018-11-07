package com.sendkar.upload.repository;

import com.sendkar.upload.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUploadername(String uploadername);
    Optional<Document> findById(Long id);
    Boolean existsByUploadername(String uploadername);
}
