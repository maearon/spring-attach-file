package com.example.springboilerplate.model;

import java.time.LocalDateTime;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "record_id")
    // private Micropost micropost;

    private String name;
    @Column(name = "record_type")
    private String recordType;
    @Column(name = "record_id")
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "blob_id")
    private ActiveStorageBlob blob;
    public Long getBlobId() {
        return blob != null ? blob.getId() : null;
    }

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // @Column(name = "updated_at")
    // private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        // updatedAt = LocalDateTime.now();
    }

    // @PreUpdate
    // protected void onUpdate() {
    //     updatedAt = LocalDateTime.now();
    // }
}
