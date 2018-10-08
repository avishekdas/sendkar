package com.sendkar.planpurchase.controller;

import com.sendkar.planpurchase.exception.ResourceNotFoundException;
import com.sendkar.planpurchase.model.User;
import com.sendkar.planpurchase.payload.*;
import com.sendkar.planpurchase.repository.UserRepository;
import com.sendkar.planpurchase.security.CurrentUser;
import com.sendkar.planpurchase.security.UserPrincipal;
import com.sendkar.planpurchase.service.UserService;
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

    @GetMapping("/plan/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByUsernameOrEmail(currentUser.getUsername(), currentUser.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username : " + currentUser.getUsername() +
                        "or email : " + currentUser.getEmail())
        );
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName()
                , user.getStatus().getName().name(), user.getPlan().getName());
        return userSummary;
    }

    @GetMapping("/plan/purchase")
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
}
