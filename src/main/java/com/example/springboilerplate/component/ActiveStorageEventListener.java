package com.example.springboilerplate.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.springboilerplate.model.ActiveStorageAttachment;
import com.example.springboilerplate.repository.ActiveStorageAttachmentRepository;
import com.example.springboilerplate.repository.ActiveStorageBlobRepository;
import com.example.springboilerplate.utils.EntityUtils;

import jakarta.persistence.PreRemove;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ActiveStorageEventListener {

    private final ActiveStorageAttachmentRepository attachmentRepo;
    private final ActiveStorageBlobRepository blobRepo;

    @PreRemove
    public void preRemove(Object entity) {
        Long id = EntityUtils.extractId(entity);
        String recordType = entity.getClass().getSimpleName();

        List<ActiveStorageAttachment> attachments = attachmentRepo.findByRecordTypeAndRecordId(recordType, id);
        for (ActiveStorageAttachment a : attachments) {
            blobRepo.deleteById(a.getBlobId());
            attachmentRepo.delete(a);
        }
    }
}
