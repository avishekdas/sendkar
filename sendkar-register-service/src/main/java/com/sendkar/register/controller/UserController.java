package com.sendkar.register.controller;

import com.sendkar.register.exception.ResourceNotFoundException;
import com.sendkar.register.model.User;
import com.sendkar.register.payload.*;
import com.sendkar.register.repository.UserRepository;
import com.sendkar.register.security.CurrentUser;
import com.sendkar.register.security.UserPrincipal;
import com.sendkar.register.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userSrvc;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/getMobileOtp")
    @PreAuthorize("hasRole('USER')")
    public OtpResponse getMobileOtp(@CurrentUser UserPrincipal currentUser) {
        OtpResponse otpResponse = userSrvc.generateMobileOtp(currentUser);
        return otpResponse;
    }

    @GetMapping("/user/getEmailOtp")
    @PreAuthorize("hasRole('USER')")
    public OtpResponse getEmailOtp(@CurrentUser UserPrincipal currentUser) {
        OtpResponse otpResponse = userSrvc.generateEmailOtp(currentUser);
        return otpResponse;
    }

    @GetMapping("/user/verifyMobileOtp")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse verifyMobileOtp(@CurrentUser UserPrincipal currentUser,
                                       @RequestParam(value = "otp") String otp) {
        ApiResponse apiResponse = new ApiResponse(false, "Verification failed");

        User user = userRepository.findByUsernameOrEmail(currentUser.getUsername(), currentUser.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username : " + currentUser.getUsername() +
                                "or email : " + currentUser.getEmail())
                );
        if(user.getMobileotp() != null && user.getMobileotp().equals(otp)){
            user.setMobileverified(1L);
            userRepository.saveAndFlush(user);
            apiResponse.setMessage("Success");
            apiResponse.setSuccess(true);
        }

        return apiResponse;
    }

    @GetMapping("/user/verifyEmailOtp")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse verifyEmailOtp(@CurrentUser UserPrincipal currentUser,
                                      @RequestParam(value = "otp") String otp) {
        ApiResponse apiResponse = new ApiResponse(false, "Verification failed");

        User user = userRepository.findByUsernameOrEmail(currentUser.getUsername(), currentUser.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username : " + currentUser.getUsername() +
                                "or email : " + currentUser.getEmail())
                );
        if(user.getEmailotp() != null && user.getEmailotp().equals(otp)){
            user.setEmailverified(1L);
            userRepository.saveAndFlush(user);
            apiResponse.setMessage("Success");
            apiResponse.setSuccess(true);
        }

        return apiResponse;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());

        return userProfile;
    }
}
