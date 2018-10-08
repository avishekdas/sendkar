package com.sendkar.upload.service;

import com.sendkar.upload.model.DocumentUploadOtp;
import com.sendkar.upload.model.User;
import com.sendkar.upload.payload.OtpResponse;
import com.sendkar.upload.repository.OtpRepository;
import com.sendkar.upload.repository.UserRepository;
import com.sendkar.upload.security.UserPrincipal;
import com.sendkar.upload.util.StringUtil;
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
    OtpRepository otpRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public OtpResponse generateDocUploadOtp(String username){
        OtpResponse otpResponse = null;
        if(username != null) {
            User user = userRepository.findByUsernameOrEmail(username, username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with username or email : " + username)
                    );

            char[] otp = StringUtil.generateOTP(4);
            otpResponse = new OtpResponse(String.valueOf(otp));

            DocumentUploadOtp docUploadOtp = new DocumentUploadOtp(username, otpResponse.getOtp());

            //Send otp <TODO>
            otpRepository.saveAndFlush(docUploadOtp);
        } else {
            new UsernameNotFoundException("User not found");
        }
        return otpResponse;
    }
}
