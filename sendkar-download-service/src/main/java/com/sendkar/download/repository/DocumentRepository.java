package com.sendkar.download.repository;

import com.sendkar.download.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUploadername(String uploadername);
    List<Document> findByReceivermobilenumber(Long receivermobilenumber);
    Optional<Document> findById(Long id);
    Boolean existsByUploadername(String uploadername);
}
