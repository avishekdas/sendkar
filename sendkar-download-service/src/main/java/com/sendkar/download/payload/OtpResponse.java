package com.sendkar.download.payload;

public class OtpResponse {
    private String otp;

    public OtpResponse(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
