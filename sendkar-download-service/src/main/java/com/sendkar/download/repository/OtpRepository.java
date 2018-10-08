package com.sendkar.download.repository;

import com.sendkar.download.model.DocumentDownloadOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<DocumentDownloadOtp, Long> {
    Optional<DocumentDownloadOtp> findByUsername(String username);
    Boolean existsByUsername(String username);
}
