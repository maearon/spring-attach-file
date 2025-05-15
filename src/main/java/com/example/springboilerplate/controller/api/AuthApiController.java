package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.JwtResponseDto;
import com.example.springboilerplate.dto.LoginDto;
import com.example.springboilerplate.dto.RegisterDto;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.security.JwtTokenProvider;
import com.example.springboilerplate.service.UserService;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.springboilerplate.security.UserPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    // public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Map<String, LoginDto> payload) {
        LoginDto loginDto = payload.get("session");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        String jwtRefresh = tokenProvider.generateRefreshToken(authentication);
        
        // User user = (User) authentication.getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        // JwtResponseDto jwtResponse = new JwtResponseDto(
        //     jwt,
        //     userPrincipal.getId(),
        //     userPrincipal.getName(),
        //     userPrincipal.getEmail(),
        //     userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
        // );
        // User user = userService.findById(userPrincipal.getId());

        Instant expiresAt = tokenProvider.getExpirationFromToken(jwt); // bạn cần cài đặt hàm này
        Instant expiresAtRefresh = tokenProvider.getExpirationFromToken(jwtRefresh); // bạn cần cài đặt hàm này

        JwtResponseDto jwtResponse = JwtResponseDto.builder()
            .user(JwtResponseDto.UserDto.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .name(userPrincipal.getName())
                .admin(userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                // .passwordHash(user.getPasswordDigest()) // cần hàm `getPasswordDigest()` trong entity
                // .token(jwt)
                .build())
            .tokens(JwtResponseDto.TokenGroupDto.builder()
                .access(JwtResponseDto.TokenDto.builder()
                    .token(jwt)
                    .expires(expiresAt)
                    .build())
                .refresh(JwtResponseDto.TokenDto.builder()
                    .token(jwtRefresh) // bạn cần tạo refresh token nếu cần
                    .expires(expiresAtRefresh)
                    .build())
                .build())
            .build();
        return ResponseEntity.ok((Object) jwtResponse); // ép kiểu về Object
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        if (userService.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Email is already taken!"));
        }

        userService.registerNewUser(
                registerDto.getName(),
                registerDto.getEmail(),
                registerDto.getPassword()
        );

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }
}
