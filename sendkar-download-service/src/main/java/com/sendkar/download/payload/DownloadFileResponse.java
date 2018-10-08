package com.sendkar.download.payload;

public class DownloadFileResponse {
    private String message;

    public DownloadFileResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
