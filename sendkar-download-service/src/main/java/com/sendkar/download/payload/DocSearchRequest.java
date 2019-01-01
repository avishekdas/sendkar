package com.sendkar.download.payload;

public class DocSearchRequest {

    private Long id;
    private String uploadername;
    private String filename;
    private String otp;
    private Long sendermobilenumber;
    private Long receivermobilenumber;
    private String senderaddress;
    private String receiveraddress;
    private String message;

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
