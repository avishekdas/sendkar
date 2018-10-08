package com.sendkar.download.model;

import com.sendkar.download.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "docdownloadotp", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        })
})
public class DocumentDownloadOtp extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    private String username;

    @Size(max = 4)
    private String otp;

    public DocumentDownloadOtp() {

    }

    public DocumentDownloadOtp(String username, String otp) {
        this.username = username;
        this.otp = otp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}