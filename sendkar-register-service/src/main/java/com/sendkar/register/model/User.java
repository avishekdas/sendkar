package com.sendkar.register.model;

import com.sendkar.register.model.audit.DateAudit;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username" +
                    ""
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String username;

    @Size(max = 100)
    private String address;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NumberFormat
    private Long mobilenumber;

    @Size(max = 4)
    private String emailotp;

    @Size(max = 4)
    private String mobileotp;

    @NumberFormat
    private Long emailverified;

    @NumberFormat
    private Long mobileverified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String name, String username, String email, String password, Long mobilenumber, String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobilenumber = mobilenumber;
        this.address = address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(Long mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmailotp() {
        return emailotp;
    }

    public void setEmailotp(String emailotp) {
        this.emailotp = emailotp;
    }

    public String getMobileotp() {
        return mobileotp;
    }

    public void setMobileotp(String mobileotp) {
        this.mobileotp = mobileotp;
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