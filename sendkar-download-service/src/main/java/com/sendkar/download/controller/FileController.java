package com.sendkar.download.controller;

import com.sendkar.download.exception.ResourceNotFoundException;
import com.sendkar.download.model.DocumentDownloadOtp;
import com.sendkar.download.payload.OtpResponse;
import com.sendkar.download.payload.DownloadFileResponse;
import com.sendkar.download.repository.OtpRepository;
import com.sendkar.download.security.CurrentUser;
import com.sendkar.download.security.UserPrincipal;
import com.sendkar.download.service.OtpService;
import com.sendkar.download.service.aws.s3.S3Services;
import com.sendkar.download.util.Utility;
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
    private OtpRepository otpRepository;

    @Autowired
    private S3Services s3Srvc;

    @Autowired
    private OtpService optSrvc;

    @PostMapping("/download/self/file")
    @PreAuthorize("hasRole('USER')")
    public DownloadFileResponse downloadFile(@CurrentUser UserPrincipal currentUser,
                                         @RequestParam("file") MultipartFile multipartFile) {
        DownloadFileResponse downloadResponse = new DownloadFileResponse("File download failed");

        //Fetch plan details
        String planName = "default-plan";
        //Validate plan <TODO>
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(planName);
        strBuff.append("/");
        strBuff.append(currentUser.getUsername());
        strBuff.append("/");

        try {
            File file = Utility.convertMultiPartToFile(multipartFile);
            String fileName = Utility.generateFileName(multipartFile);
            strBuff.append(fileName);
            s3Srvc.downloadFile(strBuff.toString());
            downloadResponse.setMessage("File downloaded successfully");
        } catch (IOException e) {
            logger.info("IOE Error Message: " + e.getMessage());
        }
        return downloadResponse;
    }

    @PostMapping("/download/self/multiplefiles")
    @PreAuthorize("hasRole('USER')")
    public List<DownloadFileResponse> downloadMultipleFiles(@CurrentUser UserPrincipal currentUser,
                                                        @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> downloadFile(currentUser, file))
                .collect(Collectors.toList());
    }

    @PostMapping("/download/assisted/file")
    @PreAuthorize("hasRole('USER')")
    public DownloadFileResponse downloadFileAssisted(@CurrentUser UserPrincipal currentUser,
                                                 @RequestParam("file") MultipartFile multipartFile,
                                                 @RequestParam("username") String username,
                                                 @RequestParam("otp") String otp) {
        DownloadFileResponse downloadResponse = new DownloadFileResponse("File download failed");
        DocumentDownloadOtp docdownloadOtp = otpRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User name not found", "username", username)
                );
        if(otp != null && otp.equals(docdownloadOtp.getOtp())) {
            //Fetch plan details
            String planName = "default-plan";
            //Validate plan <TODO>
            StringBuffer strBuff = new StringBuffer();
            strBuff.append(planName);
            strBuff.append("/");
            strBuff.append(username);
            strBuff.append("/");

            try {
                File file = Utility.convertMultiPartToFile(multipartFile);
                String fileName = Utility.generateFileName(multipartFile);
                strBuff.append(fileName);
                s3Srvc.downloadFile(strBuff.toString());
                downloadResponse.setMessage("File downloaded successfully");
            } catch (IOException e) {
                logger.info("IOE Error Message: " + e.getMessage());
            }
        }
        return downloadResponse;
    }

    @PostMapping("/download/assisted/multiplefiles")
    @PreAuthorize("hasRole('USER')")
    public List<DownloadFileResponse> downloadMultipleFilesAssisted(@CurrentUser UserPrincipal currentUser,
                                                                @RequestParam("files") MultipartFile[] files,
                                                                @RequestParam("username") String username,
                                                                @RequestParam("otp") String otp) {
        return Arrays.asList(files)
                .stream()
                .map(file -> downloadFileAssisted(currentUser, file, username, otp))
                .collect(Collectors.toList());
    }

    @GetMapping("/download/getdocdownloadotp")
    @PreAuthorize("hasRole('USER')")
    public OtpResponse getMobileOtp(@CurrentUser UserPrincipal currentUser,
                                    @RequestParam("username") String username) {
        //Fetch plan details
        String planName = "default-plan";
        //Validate plan <TODO>
        OtpResponse otpResponse = optSrvc.generateDocDownloadOtp(username);
        return otpResponse;
    }
}
