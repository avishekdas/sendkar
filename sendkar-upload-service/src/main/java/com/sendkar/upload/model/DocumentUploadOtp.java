package com.sendkar.upload.model;

import com.sendkar.upload.model.audit.DateAudit;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "docuploadotp", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        })
})
public class DocumentUploadOtp extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    private String username;

    @Size(max = 4)
    private String otp;

    public DocumentUploadOtp() {

    }

    public DocumentUploadOtp(String username, String otp) {
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