package com.sendkar.download.repository;

import com.sendkar.download.model.DocDownloadDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocDownloadDtlsRepository extends JpaRepository<DocDownloadDtls, Long> {
    List<DocDownloadDtls> findByDocid(Long docid);
    List<DocDownloadDtls> findByUsername(String username);
}
