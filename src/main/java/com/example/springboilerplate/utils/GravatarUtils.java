package com.example.springboilerplate.utils;

import org.springframework.util.DigestUtils;

public class GravatarUtils {
    
    public static String getGravatarUrl(String email, int size) {
        if (email == null) return "";

        String normalizedEmail = email.trim().toLowerCase();
        String hash = DigestUtils.md5DigestAsHex(normalizedEmail.getBytes());

        return "https://secure.gravatar.com/avatar/" + hash + "?s=" + size;
    }
}
