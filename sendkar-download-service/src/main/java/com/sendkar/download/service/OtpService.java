package com.sendkar.download.service;

import com.sendkar.download.exception.ResourceNotFoundException;
import com.sendkar.download.model.Document;
import com.sendkar.download.payload.OtpResponse;
import com.sendkar.download.repository.DocumentRepository;
import com.sendkar.download.repository.UserRepository;
import com.sendkar.download.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public OtpResponse generateDocOtp(Long docId){
        OtpResponse otpResponse = null;
        if(docId != null) {
            Document document = documentRepository.findById(docId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Document", "Document Id", docId)
                    );
            char[] otp = StringUtil.generateOTP(4);
            otpResponse = new OtpResponse(String.valueOf(otp));
            if(document != null){
                document.setOtp(otpResponse.getOtp());
            }
            //Send otp <TODO>
            documentRepository.saveAndFlush(document);
        } else {
            new UsernameNotFoundException("User not found");
        }
        return otpResponse;
    }
}
