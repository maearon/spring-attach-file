package com.example.springboilerplate.utils;

import java.lang.reflect.Field;

public class EntityUtils {
    public static Long extractId(Object entity) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return (Long) field.get(entity);
        } catch (Exception e) {
            throw new RuntimeException("Cannot extract ID from entity", e);
        }
    }
}
