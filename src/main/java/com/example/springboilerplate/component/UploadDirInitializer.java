package com.example.springboilerplate.component;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadDirInitializer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("✅ Created upload directory: " + dir.getAbsolutePath());
            } else {
                System.err.println("❌ Failed to create upload directory.");
            }
        } else {
            System.out.println("✅ Upload directory already exists: " + dir.getAbsolutePath());
        }
    }
}