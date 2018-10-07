package com.sendkar.upload.controller;

import com.sendkar.upload.payload.UploadFileResponse;
import com.sendkar.upload.security.CurrentUser;
import com.sendkar.upload.security.UserPrincipal;
import com.sendkar.upload.service.aws.s3.S3Services;
import com.sendkar.upload.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private S3Services s3Srvc;

    @PostMapping("/upload/self/file")
    @PreAuthorize("hasRole('USER')")
    public UploadFileResponse uploadFile(@CurrentUser UserPrincipal currentUser,
                                         @RequestParam("file") MultipartFile multipartFile) {
        UploadFileResponse uploadResponse = new UploadFileResponse("File upload failed");

        //Fetch plan details
        String planName = "default-plan"; //<TODO>
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(planName);
        strBuff.append("/");
        strBuff.append(currentUser.getUsername());
        strBuff.append("/");

        try {
            File file = Utility.convertMultiPartToFile(multipartFile);
            String fileName = Utility.generateFileName(multipartFile);
            strBuff.append(fileName);
            s3Srvc.uploadFile(strBuff.toString(), file);
            uploadResponse.setMessage("File uploaded successfully");
        } catch (IOException e) {
            logger.info("IOE Error Message: " + e.getMessage());
        }
        return uploadResponse;
    }

    @PostMapping("/upload/self/multiplefiles")
    @PreAuthorize("hasRole('USER')")
    public List<UploadFileResponse> uploadMultipleFiles(@CurrentUser UserPrincipal currentUser,
                                                        @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(currentUser, file))
                .collect(Collectors.toList());
    }

    @PostMapping("/upload/assisted/file")
    @PreAuthorize("hasRole('USER')")
    public UploadFileResponse uploadFileAssisted(@CurrentUser UserPrincipal currentUser,
                                                 @RequestParam("file") MultipartFile multipartFile,
                                                 @RequestParam("username") String username,
                                                 @RequestParam("otp") String otp) {
        UploadFileResponse uploadResponse = new UploadFileResponse("File upload failed");



        //Fetch plan details
        String planName = "default-plan"; //<TODO>
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(planName);
        strBuff.append("/");
        strBuff.append(currentUser.getUsername());
        strBuff.append("/");

        try {
            File file = Utility.convertMultiPartToFile(multipartFile);
            String fileName = Utility.generateFileName(multipartFile);
            strBuff.append(fileName);
            s3Srvc.uploadFile(strBuff.toString(), file);
            uploadResponse.setMessage("File uploaded successfully");
        } catch (IOException e) {
            logger.info("IOE Error Message: " + e.getMessage());
        }
        return uploadResponse;
    }

    @PostMapping("/upload/assisted/multiplefiles")
    @PreAuthorize("hasRole('USER')")
    public List<UploadFileResponse> uploadMultipleFilesAssisted(@CurrentUser UserPrincipal currentUser,
                                                                @RequestParam("files") MultipartFile[] files,
                                                                @RequestParam("username") String username,
                                                                @RequestParam("otp") String otp) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFileAssisted(currentUser, file, username, otp))
                .collect(Collectors.toList());
    }
}
