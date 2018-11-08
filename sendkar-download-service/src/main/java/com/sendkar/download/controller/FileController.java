package com.sendkar.download.controller;

import com.sendkar.download.converters.DocumentToDocumentResponse;
import com.sendkar.download.exception.ResourceNotFoundException;
import com.sendkar.download.model.Document;
import com.sendkar.download.payload.ApiResponse;
import com.sendkar.download.payload.DocumentResponse;
import com.sendkar.download.payload.DownloadFileResponse;
import com.sendkar.download.repository.DocumentRepository;
import com.sendkar.download.security.CurrentUser;
import com.sendkar.download.security.UserPrincipal;
import com.sendkar.download.service.OtpService;
import com.sendkar.download.service.aws.S3Services;
import com.sendkar.download.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private DocumentToDocumentResponse docToDocResponseConvertor;

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

    /*@PostMapping("/download/assisted/multiplefiles")
    @PreAuthorize("hasRole('USER')")
    public List<DownloadFileResponse> downloadMultipleFilesAssisted(@CurrentUser UserPrincipal currentUser,
                                                                @RequestParam("files") MultipartFile[] files,
                                                                @RequestParam("username") String username,
                                                                @RequestParam("otp") String otp) {
        return Arrays.asList(files)
                .stream()
                .map(file -> downloadFileAssisted(currentUser, file, username, otp))
                .collect(Collectors.toList());
    }*/

    @GetMapping("/download/assisted/file/{id}/{otp}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<byte[]> downloadFileAssisted(@PathVariable Long id,
                                                       @PathVariable String otp) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document Id not found", "id", id)
                );

        if(otp != null && otp.equals(document.getOtp())) {
            //Fetch plan details
            String planName = "default-plan";
            //Validate plan <TODO>
            StringBuffer strBuff = new StringBuffer();
            strBuff.append(planName);
            strBuff.append("/");
            strBuff.append(document.getReceivermobilenumber());
            strBuff.append("/");
            strBuff.append(document.getFilename());

            ByteArrayOutputStream downloadInputStream = s3Srvc.downloadFile(strBuff.toString());

            return ResponseEntity.ok()
                    .contentType(contentType(strBuff.toString()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + strBuff.toString() + "\"")
                    .body(downloadInputStream.toByteArray());
        }
        return null;
    }

    private MediaType contentType(String keyname) {
        String[] arr = keyname.split("\\.");
        String type = arr[arr.length-1];
        switch(type) {
            case "txt": return MediaType.TEXT_PLAIN;
            case "png": return MediaType.IMAGE_PNG;
            case "jpg": return MediaType.IMAGE_JPEG;
            default: return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @GetMapping("/download/getdocumentlist/{receivermobilenumber}")
    public List<DocumentResponse> getDocumentList(@PathVariable Long receivermobilenumber) {
        List<DocumentResponse> docRespList = new ArrayList<DocumentResponse>();
        List<Document> documentList = documentRepository.findByReceivermobilenumber(receivermobilenumber);

        for (Document doc: documentList
             ) {
            docRespList.add(docToDocResponseConvertor.convert(doc));
        }

        return docRespList;
    }

    @GetMapping("/download/getdocdownloadotp/{id}")
//    @PreAuthorize("hasRole('USER')")
    public ApiResponse getMobileOtp(@PathVariable Long id) {
        return optSrvc.generateDocOtp(id);
    }
}
