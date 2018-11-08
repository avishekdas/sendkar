package com.sendkar.download.service.aws;

public interface SNSServices {
    public boolean sendSms(String mobileNumber, String message);
}
