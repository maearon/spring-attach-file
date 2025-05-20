package com.example.springboilerplate.repository;

import com.example.springboilerplate.model.ActiveStorageAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiveStorageAttachmentRepository extends JpaRepository<ActiveStorageAttachment, Long> {
    // List<ActiveStorageAttachment> findByRecordTypeAndBlobKey(String recordType, String blobKey);
    @Query("SELECT a FROM ActiveStorageAttachment a WHERE a.recordType = :recordType AND a.blob.key = :blobKey")
    List<ActiveStorageAttachment> findByRecordTypeAndBlobKey(@Param("recordType") String recordType, @Param("blobKey") String blobKey);
    List<ActiveStorageAttachment> findByRecordTypeAndRecordId(String recordType, Long recordId);
    List<ActiveStorageAttachment> findByRecordTypeAndRecordIdAndName(String recordType, Long recordId, String name);
}
