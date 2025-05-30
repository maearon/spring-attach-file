package com.example.springboilerplate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String username;

     @NotBlank
     @Size(min = 3, max = 50)
     @Column(name = "\"displayName\"", nullable = true)
     private String displayName;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 6)
    @Column(name = "password_digest", nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean admin = false;

    @Column(name = "activation_digest")
    private String activationDigest;

    @Column(name = "activated")
    private boolean activated = false;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    @Column(name = "reset_digest")
    private String resetDigest;

    @Column(name = "reset_sent_at")
    private LocalDateTime resetSentAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expiration_at")
    private LocalDateTime refreshTokenExpirationAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "remember_digest")
    private String rememberDigest;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Micropost> microposts = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relationship> following = new ArrayList<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relationship> followers = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (admin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }

    public String getGravatar() {
        String validEmail = (email != null) ? email.trim().toLowerCase() : "test@example.com";
        String emailHash = DigestUtils.md5DigestAsHex(validEmail.trim().toLowerCase().getBytes());
        return "https://secure.gravatar.com/avatar/" + emailHash + "?s=80";
    }
}
