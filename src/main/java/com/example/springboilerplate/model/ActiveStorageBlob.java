package com.example.springboilerplate.model;

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
    @Id @GeneratedValue
    private Long id;
    private String key;
    private String filename;
    private String contentType;
    private long byteSize;
    private String checksum;
    private String serviceName = "local";
}

