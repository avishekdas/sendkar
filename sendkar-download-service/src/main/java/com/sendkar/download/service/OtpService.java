package com.sendkar.download.service;

import com.sendkar.download.model.DocumentDownloadOtp;
import com.sendkar.download.model.User;
import com.sendkar.download.payload.OtpResponse;
import com.sendkar.download.repository.OtpRepository;
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
    OtpRepository otpRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public OtpResponse generateDocDownloadOtp(String username){
        OtpResponse otpResponse = null;
        if(username != null) {
            User user = userRepository.findByUsernameOrEmail(username, username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with username or email : " + username)
                    );

            char[] otp = StringUtil.generateOTP(4);
            otpResponse = new OtpResponse(String.valueOf(otp));

            DocumentDownloadOtp docDownloadOtp = new DocumentDownloadOtp(username, otpResponse.getOtp());

            //Send otp <TODO>
            otpRepository.saveAndFlush(docDownloadOtp);
        } else {
            new UsernameNotFoundException("User not found");
        }
        return otpResponse;
    }
}
