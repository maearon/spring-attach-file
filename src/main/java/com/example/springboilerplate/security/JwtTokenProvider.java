package com.example.springboilerplate.security;

import org.springframework.security.core.AuthenticationException;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret:JWTSuperSecretKey}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs:604800000}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setSubject(userPrincipal.getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs * 2L); // dài hơn access token

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setSubject(userPrincipal.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getUserIdFromJWT(String token) {
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(jwtSecret.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject(); // ✅ trả về String
    }

    public Instant getExpirationFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        Date expiration = claims.getExpiration();
        return expiration.toInstant();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            throw new TokenExpiredException("JWT token has expired");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty");
        }
        return false;
    }

    public class TokenExpiredException extends AuthenticationException {
        public TokenExpiredException(String msg) {
            super(msg);
        }
    }
}
