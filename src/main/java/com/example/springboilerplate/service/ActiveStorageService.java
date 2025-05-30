package com.example.springboilerplate.service;

import com.example.springboilerplate.model.ActiveStorageAttachment;
import com.example.springboilerplate.model.ActiveStorageBlob;
import com.example.springboilerplate.repository.ActiveStorageAttachmentRepository;
import com.example.springboilerplate.repository.ActiveStorageBlobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActiveStorageService {

    private final ActiveStorageBlobRepository blobRepository;
    private final ActiveStorageAttachmentRepository attachmentRepository;
    private final AttachmentRecordResolver recordResolver;
    private final Path uploadPath = Paths.get("uploads");

    public void attach(MultipartFile file, String recordType, Long recordId, String name) {
        try {
            // Lưu file thật (tùy bạn config S3, local, cloud)
            String key = UUID.randomUUID().toString();
            Path path = Paths.get("uploads", key);
            Files.copy(file.getInputStream(), path);

            // Lưu blob
            ActiveStorageBlob blob = new ActiveStorageBlob();
            blob.setKey(key);
            blob.setFilename(file.getOriginalFilename()); // 7358dce9-6619-444e-a92f-79b03e3381f8.jpeg
            blob.setContentType(file.getContentType());
            // {"identified": true,"width": 347,"height": 351,"analyzed": true}
            blob.setByteSize(file.getSize());
            blob.setChecksum(Base64.getEncoder().encodeToString(file.getBytes())); // có thể hash SHA256 thay thế
            blobRepository.save(blob);

            // Lưu attachment
            ActiveStorageAttachment attach = new ActiveStorageAttachment();
            attach.setName(name);
            attach.setRecordType(recordType);
            attach.setRecordId(recordId);
            attach.setBlob(blob);
            attachmentRepository.save(attach);

        } catch (IOException e) {
            e.printStackTrace(); // In stack trace ra console
            throw new RuntimeException("Upload failed", e);
        }
    }

    public List<String> findAttachments(String recordType, Long recordId, String name) {
    List<ActiveStorageAttachment> attachments = attachmentRepository
        .findByRecordTypeAndRecordIdAndName(recordType, recordId, name);

    return attachments.stream()
            .map(att -> "/uploads/" + att.getBlob().getKey())
            .toList();
    }

    public void deleteAttachments(String recordType, String blobKey) {
    List<ActiveStorageAttachment> attachments = attachmentRepository
        .findByRecordTypeAndBlobKey(recordType, blobKey);

        for (ActiveStorageAttachment attachment : attachments) {
            ActiveStorageBlob blob = attachment.getBlob();

            // Xoá file vật lý (nếu có)
            Path path = Paths.get("uploads", blob.getKey());
            try { Files.deleteIfExists(path); } catch (IOException ignored) {}

            attachmentRepository.delete(attachment);
            blobRepository.delete(blob);
        }
    }

}

