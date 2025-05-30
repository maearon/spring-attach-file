package com.example.springboilerplate.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "active_storage_blobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveStorageBlob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
    private String filename;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "byte_size")
    private long byteSize;
    private String checksum;
    @Column(name = "service_name")
    private String serviceName = "local";

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

