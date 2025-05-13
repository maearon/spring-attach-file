package com.example.springboilerplate.service;

import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.repository.MicropostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MicropostService {

    private final MicropostRepository micropostRepository;
    private final Path uploadPath = Paths.get("uploads");

    public MicropostService() {
        this.micropostRepository = null;
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public Page<Micropost> findAll(Pageable pageable) {
        return micropostRepository.findAll(pageable);
    }

    public Optional<Micropost> findById(Long id) {
        return micropostRepository.findById(id);
    }

    public List<Micropost> findByUserId(Long userId) {
        return micropostRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Page<Micropost> findByUserId(Long userId, Pageable pageable) {
        return micropostRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    public Page<Micropost> getFeed(Long userId, Pageable pageable) {
        return micropostRepository.findFeed(userId, pageable);
    }

    @Transactional
    public Micropost create(Micropost micropost, MultipartFile picture) {
        if (picture != null && !picture.isEmpty()) {
            try {
                String filename = UUID.randomUUID() + "_" + picture.getOriginalFilename();
                Files.copy(picture.getInputStream(), uploadPath.resolve(filename));
                micropost.setPicture(filename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }
        return micropostRepository.save(micropost);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Micropost> micropostOpt = micropostRepository.findById(id);
        if (micropostOpt.isPresent()) {
            Micropost micropost = micropostOpt.get();
            if (micropost.getPicture() != null) {
                try {
                    Files.deleteIfExists(uploadPath.resolve(micropost.getPicture()));
                } catch (IOException e) {
                    // Log error but continue with deletion
                }
            }
            micropostRepository.deleteById(id);
        }
    }
}
