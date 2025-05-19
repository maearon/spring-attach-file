package com.example.springboilerplate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "active_storage_attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveStorageAttachment {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String recordType;
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "blob_id")
    private ActiveStorageBlob blob;
    public Long getBlobId() {
        return blob != null ? blob.getId() : null;
    }
}
