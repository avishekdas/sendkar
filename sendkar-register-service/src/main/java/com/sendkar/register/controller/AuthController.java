package com.sendkar.register.controller;

import com.sendkar.register.exception.AppException;
import com.sendkar.register.model.Role;
import com.sendkar.register.model.RoleName;
import com.sendkar.register.model.User;
import com.sendkar.register.payload.*;
import com.sendkar.register.repository.RoleRepository;
import com.sendkar.register.repository.UserRepository;
import com.sendkar.register.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getMobilenumber()
                , signUpRequest.getAddress());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest updateRequest) {

        User user = userRepository.findByUsernameOrEmail(updateRequest.getUsername(), updateRequest.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username : " + updateRequest.getUsername() +
                                "or email : " + updateRequest.getEmail())
                );

        //<TODO> check if data can be removed
        if(updateRequest.getAddress() != null && !"".equalsIgnoreCase(updateRequest.getAddress()))
            user.setAddress(updateRequest.getAddress());
        if(updateRequest.getMobilenumber() != null && updateRequest.getMobilenumber() != 0)
            user.setMobilenumber(updateRequest.getMobilenumber());
        if(updateRequest.getName() != null && !"".equalsIgnoreCase(updateRequest.getName()))
            user.setName(updateRequest.getName());
        if(updateRequest.getUsername() != null && !"".equalsIgnoreCase(updateRequest.getUsername()))
            user.setUsername(updateRequest.getUsername());
        if(updateRequest.getEmail() != null && !"".equalsIgnoreCase(updateRequest.getEmail()))
            user.setEmail(updateRequest.getEmail());
        if(updateRequest.getPassword() != null && !"".equalsIgnoreCase(updateRequest.getPassword()))
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User updated successfully"));
    }
}
