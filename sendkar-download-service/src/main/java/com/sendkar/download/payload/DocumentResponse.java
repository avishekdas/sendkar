package com.sendkar.download.payload;

import com.sendkar.download.model.DocDownloadDtls;

import java.util.List;

public class DocumentResponse {
    private Long id;
    private String uploadername;
    private String filename;
    private String otp;
    private Long sendermobilenumber;
    private Long receivermobilenumber;
    private String senderaddress;
    private String receiveraddress;
    private String message;
    private Integer pagecount;
    private Integer downloadcount;

    private List<DocDownloadDtls> docDownloadDtlsList;

    public DocumentResponse() {
    }

    public DocumentResponse(Long id, String uploadername, String filename, String otp,
                            Long sendermobilenumber, Long receivermobilenumber,
                            String senderaddress, String receiveraddress, String message,
                            Integer pagecount, Integer downloadcount) {
        this.id = id;
        this.uploadername = uploadername;
        this.filename = filename;
        this.otp = otp;
        this.sendermobilenumber = sendermobilenumber;
        this.receivermobilenumber = receivermobilenumber;
        this.senderaddress = senderaddress;
        this.receiveraddress = receiveraddress;
        this.message = message;
        this.pagecount = pagecount;
        this.downloadcount = downloadcount;
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

    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public Integer getDownloadcount() {
        return downloadcount;
    }

    public void setDownloadcount(Integer downloadcount) {
        this.downloadcount = downloadcount;
    }

    public List<DocDownloadDtls> getDocDownloadDtlsList() {
        return docDownloadDtlsList;
    }

    public void setDocDownloadDtlsList(List<DocDownloadDtls> docDownloadDtlsList) {
        this.docDownloadDtlsList = docDownloadDtlsList;
    }
}
