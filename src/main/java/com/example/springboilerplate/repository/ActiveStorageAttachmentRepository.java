package com.example.springboilerplate.repository;

import com.example.springboilerplate.model.ActiveStorageAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiveStorageAttachmentRepository extends JpaRepository<ActiveStorageAttachment, Long> {
    List<ActiveStorageAttachment> findByRecordTypeAndRecordId(String recordType, Long recordId);
    List<ActiveStorageAttachment> findByRecordTypeAndRecordIdAndName(String recordType, Long recordId, String name);
}
