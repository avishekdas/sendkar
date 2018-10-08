package com.sendkar.upload.repository;

import com.sendkar.upload.model.DocumentUploadOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<DocumentUploadOtp, Long> {
    Optional<DocumentUploadOtp> findByUsername(String username);
    Boolean existsByUsername(String username);
}
