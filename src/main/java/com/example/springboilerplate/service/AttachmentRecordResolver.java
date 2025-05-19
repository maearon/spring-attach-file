package com.example.springboilerplate.service;

import com.example.springboilerplate.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class AttachmentRecordResolver {

    @PersistenceContext
    private EntityManager entityManager;

    public Object resolveRecord(String recordType, Long recordId) {
        if (recordType == null || recordId == null) return null;

        return switch (recordType) {
            case "Micropost" -> entityManager.find(Micropost.class, recordId);
            case "User" -> entityManager.find(User.class, recordId);
            // ✅ Thêm các entity khác nếu cần
            default -> null;
        };
    }
}
