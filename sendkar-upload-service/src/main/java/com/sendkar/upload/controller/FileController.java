package com.sendkar.upload.controller;

import com.sendkar.upload.model.Document;
import com.sendkar.upload.payload.OtpResponse;
import com.sendkar.upload.payload.UploadFileResponse;
import com.sendkar.upload.repository.DocumentRepository;
import com.sendkar.upload.security.CurrentUser;
import com.sendkar.upload.security.UserPrincipal;
import com.sendkar.upload.service.OtpService;
import com.sendkar.upload.service.aws.s3.S3Services;
import com.sendkar.upload.util.StringUtil;
import com.sendkar.upload.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private S3Services s3Srvc;

    @Autowired
    private OtpService optSrvc;

    @PostMapping("/upload/self/file")
    @PreAuthorize("hasRole('USER')")
    public UploadFileResponse uploadFile(@CurrentUser UserPrincipal currentUser,
                                         @RequestParam("file") MultipartFile multipartFile) {
        UploadFileResponse uploadResponse = new UploadFileResponse("File upload failed");

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
//    @PreAuthorize("hasRole('USER')")
    public UploadFileResponse uploadFileAssisted(@RequestParam("file") MultipartFile multipartFile,
                                                 @RequestParam("uploadername") String uploadername,
                                                 @RequestParam("sendermobilenumber") String sendermobilenumber,
                                                 @RequestParam("receivermobilenumber") String receivermobilenumber,
                                                 @RequestParam("senderaddress") String senderaddress,
                                                 @RequestParam("receiveraddress") String receiveraddress,
                                                 @RequestParam("message") String message) {
        UploadFileResponse uploadResponse = new UploadFileResponse("File upload failed");
        StringBuffer otp = new StringBuffer();
        otp.append(StringUtil.generateOTP(4));
        if(otp != null && !"".equalsIgnoreCase(otp.toString())) {
            //Fetch plan details
            String planName = "default-plan";
            //Validate plan <TODO>
            StringBuffer strBuff = new StringBuffer();
            strBuff.append(planName);
            strBuff.append("/");
            strBuff.append(receivermobilenumber);
            strBuff.append("/");

            try {
                File file = Utility.convertMultiPartToFile(multipartFile);
                String fileName = Utility.generateFileName(multipartFile);
                strBuff.append(fileName);
                s3Srvc.uploadFile(strBuff.toString(), file);

                Long sendermobilenumberLong = Long.parseLong(sendermobilenumber);
                Long receivermobilenumberLong = Long.parseLong(receivermobilenumber);

                Document document = new Document(uploadername, multipartFile.getOriginalFilename(), otp.toString(),
                        sendermobilenumberLong, receivermobilenumberLong,
                        senderaddress, receiveraddress, message);

                documentRepository.saveAndFlush(document);

                uploadResponse.setMessage("File uploaded successfully");

            } catch (IOException e) {
                logger.info("IOE Error Message: " + e.getMessage());
            }
        }
        return uploadResponse;
    }

    /*@PostMapping("/upload/assisted/multiplefiles")
    @PreAuthorize("hasRole('USER')")
    public List<UploadFileResponse> uploadMultipleFilesAssisted(@CurrentUser UserPrincipal currentUser,
                                                                @RequestParam("files") MultipartFile[] files,
                                                                @RequestParam("username") String username,
                                                                @RequestParam("otp") String otp) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFileAssisted(currentUser, file, username, otp))
                .collect(Collectors.toList());
    }*/

    @GetMapping("/upload/getdocumentotp")
//    @PreAuthorize("hasRole('USER')")
    public OtpResponse getMobileOtp(@RequestParam("id") String docId) {
        //Fetch plan details
        String planName = "default-plan";
        //Validate plan <TODO>
        Long docIdLong = Long.parseLong(docId);
        OtpResponse otpResponse = optSrvc.generateDocOtp(docIdLong);
        return otpResponse;
    }
}
