package com.sendkar.register.payload;

public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private String address;
    private String email;
    private Long mobilenumber;
    private Long emailverified;
    private Long mobileverified;


    public UserProfile(Long id, String username, String name, String address, String email,
                       Long mobilenumber, Long emailverified, Long mobileverified) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.address = address;
        this.email = email;
        this.mobilenumber = mobilenumber;
        this.emailverified = emailverified;
        this.mobileverified = mobileverified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(Long mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public Long getEmailverified() {
        return emailverified;
    }

    public void setEmailverified(Long emailverified) {
        this.emailverified = emailverified;
    }

    public Long getMobileverified() {
        return mobileverified;
    }

    public void setMobileverified(Long mobileverified) {
        this.mobileverified = mobileverified;
    }
}
