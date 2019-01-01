package com.sendkar.download.model;

import com.sendkar.download.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "doc_download_map")
public class DocDownloadDtls extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter document id")
    private Long docid;

    @NotBlank
    @Size(max = 100)
    private String username;

    public DocDownloadDtls() {
    }

    public DocDownloadDtls(@NotBlank @Size(max = 100) String username,
                           @NotNull(message = "Please enter document id")
                                  Long docid) {
        this.username = username;
        this.docid = docid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocid() {
        return docid;
    }

    public void setDocid(Long docid) {
        this.docid = docid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}