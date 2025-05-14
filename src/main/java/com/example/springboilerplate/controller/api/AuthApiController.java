package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.JwtResponseDto;
import com.example.springboilerplate.dto.LoginDto;
import com.example.springboilerplate.dto.RegisterDto;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.security.JwtTokenProvider;
import com.example.springboilerplate.service.UserService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        // User user = (User) authentication.getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        JwtResponseDto jwtResponse = new JwtResponseDto(
            jwt,
            userPrincipal.getId(),
            userPrincipal.getName(),
            userPrincipal.getEmail(),
            userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
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
