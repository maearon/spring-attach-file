package com.example.springboilerplate.utils;

import org.springframework.util.DigestUtils;

public class GravatarUtils {
    
    public static String getGravatarUrl(String email, int size) {
        String validEmail = (email != null) ? email.trim().toLowerCase() : "test@example.com";
        String normalizedEmail = validEmail.trim().toLowerCase();
        String hash = DigestUtils.md5DigestAsHex(normalizedEmail.getBytes());

        return "https://secure.gravatar.com/avatar/" + hash + "?s=" + size;
    }
}
