package com.sendkar.download.model;

import com.sendkar.download.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "document")
public class Document extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String uploadername;

    @NotBlank
    @Size(max = 100)
    private String filename;

    @Size(max = 4)
    private String otp;

    @NotNull(message = "Please enter sender mobile number")
    private Long sendermobilenumber;

    @NotNull(message = "Please enter receiver mobile number")
    private Long receivermobilenumber;

    @NotBlank
    private String senderaddress;

    @NotBlank
    private String receiveraddress;

    @Size(max = 200)
    private String message;

    public Document() {
    }

    public Document(@NotBlank @Size(max = 100) String uploadername,
                    @NotBlank @Size(max = 100) String filename, @Size(max = 4)
            String otp, @NotNull(message = "Please enter sender mobile number")
                            Long sendermobilenumber, @NotNull(message = "Please enter receiver mobile number")
                            Long receivermobilenumber, @NotBlank String senderaddress, @NotBlank String receiveraddress, String message) {
        this.uploadername = uploadername;
        this.filename = filename;
        this.otp = otp;
        this.sendermobilenumber = sendermobilenumber;
        this.receivermobilenumber = receivermobilenumber;
        this.senderaddress = senderaddress;
        this.receiveraddress = receiveraddress;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUploadername() {
        return uploadername;
    }

    public void setUploadername(String uploadername) {
        this.uploadername = uploadername;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Long getSendermobilenumber() {
        return sendermobilenumber;
    }

    public void setSendermobilenumber(Long sendermobilenumber) {
        this.sendermobilenumber = sendermobilenumber;
    }

    public Long getReceivermobilenumber() {
        return receivermobilenumber;
    }

    public void setReceivermobilenumber(Long receivermobilenumber) {
        this.receivermobilenumber = receivermobilenumber;
    }

    public String getSenderaddress() {
        return senderaddress;
    }

    public void setSenderaddress(String senderaddress) {
        this.senderaddress = senderaddress;
    }

    public String getReceiveraddress() {
        return receiveraddress;
    }

    public void setReceiveraddress(String receiveraddress) {
        this.receiveraddress = receiveraddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}