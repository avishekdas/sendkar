package com.sendkar.download.service.aws.s3;

import java.io.File;

public interface S3Services {
    public String downloadFile(String keyName);
    public void uploadFile(String keyName, File file);
}
