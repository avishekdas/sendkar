package com.sendkar.register.service;

import com.sendkar.register.model.User;
import com.sendkar.register.payload.OtpResponse;
import com.sendkar.register.payload.UserProfile;
import com.sendkar.register.repository.UserRepository;
import com.sendkar.register.security.UserPrincipal;
import com.sendkar.register.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Transactional
    public OtpResponse generateMobileOtp(UserPrincipal currentUser){
        OtpResponse otpResponse = null;
        if(currentUser != null) {
            User user = userRepository.findByUsernameOrEmail(currentUser.getUsername(), currentUser.getEmail())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with username or email : " + currentUser.getUsername())
                    );

            char[] otp = StringUtil.generateOTP(4);
            otpResponse = new OtpResponse(String.valueOf(otp));

            user.setMobileotp(otpResponse.getOtp());
            userRepository.saveAndFlush(user);
        } else {
            new UsernameNotFoundException("User not found");
        }
        return otpResponse;
    }

    @Transactional
    public OtpResponse generateEmailOtp(UserPrincipal currentUser){
        OtpResponse otpResponse = null;
        if(currentUser != null) {
            User user = userRepository.findByUsernameOrEmail(currentUser.getUsername(), currentUser.getEmail())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with username or email : " + currentUser.getUsername())
                    );

            char[] otp = StringUtil.generateOTP(4);
            otpResponse = new OtpResponse(String.valueOf(otp));

            user.setEmailotp(otpResponse.getOtp());
            userRepository.saveAndFlush(user);
        } else {
            new UsernameNotFoundException("User not found");
        }
        return otpResponse;
    }

    @Transactional
    public UserProfile getUsrDetails(String usrName){
        UserProfile usrProfile = null;
        if(usrName != null) {
            User user = userRepository.findByUsername(usrName)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with username : " + usrName)
                    );

            usrProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(),
                    user.getAddress(), user.getEmail(), user.getMobilenumber(),
                    user.getEmailverified(), user.getMobileverified());
        } else {
            new UsernameNotFoundException("User not found");
        }
        return usrProfile;
    }
}
