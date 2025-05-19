package com.example.springboilerplate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
public class ImageAttachment {

    @Id
    private String id;

    private String fileName;
    private String fileType;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "micropost_id")
    private Micropost micropost;

    // Getters, Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Micropost getMicropost() {
        return micropost;
    }
    public void setMicropost(Micropost micropost) {
        this.micropost = micropost;
    }
    // Constructors
    public ImageAttachment() {
    }
    public ImageAttachment(String id, String fileName, String fileType, String filePath, Micropost micropost) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.micropost = micropost;
    }
    // toString
    @Override
    public String toString() {
        return "ImageAttachment{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", filePath='" + filePath + '\'' +
                ", micropost=" + micropost +
                '}';
    }
    // equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageAttachment)) return false;
        ImageAttachment that = (ImageAttachment) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    // Other methods
    public String getFileNameWithoutExtension() {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return fileName;
    }
    public String getFileExtension() {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return null;
    }
    public String getFileSize() {
        return filePath != null ? filePath : "0";
    }
    public String getFileSizeInKB() {
        return filePath != null ? String.valueOf(filePath.length() / 1024) : "0";
    }
    public String getFileSizeInMB() {
        return filePath != null ? String.valueOf(filePath.length() / (1024 * 1024)) : "0";
    }
    public String getFileSizeInGB() {
        return filePath != null ? String.valueOf(filePath.length() / (1024 * 1024 * 1024)) : "0";
    }
    public String getFileSizeInTB() {
        return filePath != null ? String.valueOf(filePath.length() / (1024 * 1024 * 1024 * 1024)) : "0";
    }
    public String getFileSizeInPB() {
        return filePath != null ? String.valueOf(filePath.length() / (1024 * 1024 * 1024 * 1024 * 1024)) : "0";
    }
    public String getFileSizeInEB() {
        return filePath != null ? String.valueOf(filePath.length() / (1024 * 1024 * 1024 * 1024 * 1024 * 1024)) : "0";
    }
    public String getFileSizeInZB() {
        return filePath != null ? String.valueOf(filePath.length() / (1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024)) : "0";
    }       
    public String getFileSizeInYB() {
        return filePath != null ? String.valueOf(filePath.length() / (1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024)) : "0";
    }
    public String getImages() {
        return filePath != null ? filePath : "0";
    }
    public String getImage() {
        return filePath != null ? filePath : "0";
    }
}  