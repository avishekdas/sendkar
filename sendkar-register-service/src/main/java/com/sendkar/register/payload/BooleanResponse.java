package com.sendkar.register.payload;

public class BooleanResponse {
    private Boolean status;

    public BooleanResponse(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
