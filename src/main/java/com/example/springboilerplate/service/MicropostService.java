package com.example.springboilerplate.service;

import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.repository.MicropostRepository;
import com.example.springboilerplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.springboilerplate.dto.MicropostResponseDto;
import com.example.springboilerplate.dto.UserSummaryDto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MicropostService {

    private final MicropostRepository micropostRepository;
    private final UserRepository userRepository;
    private final ActiveStorageService attachmentService;

    private final Path uploadPath = Paths.get("uploads");

    @Transactional
    public Micropost create(String userId, String content, MultipartFile[] images) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Micropost micropost = new Micropost();
        micropost.setUser(user);
        micropost.setContent(content);
        micropostRepository.save(micropost);

        // if (picture != null && !picture.isEmpty()) {
        //     String filename = UUID.randomUUID() + "_" + picture.getOriginalFilename();
        //     Files.createDirectories(uploadPath); // ensure directory exists
        //     Files.copy(picture.getInputStream(), uploadPath.resolve(filename));
        //     micropost.setPicture(filename);
        // }

        if (images != null) {
            for (MultipartFile file : images) {
                attachmentService.attach(file, "Micropost", micropost.getId(), "images");
            }
        }

        return micropost;
    }

    public Page<Micropost> findAll(Pageable pageable) {
        return micropostRepository.findAll(pageable);
    }

    public Optional<Micropost> findById(Long id) {
        return micropostRepository.findById(id);
    }

    public List<Micropost> findByUserId(String userId) {
        return micropostRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Page<Micropost> findByUserId(String userId, Pageable pageable) {
        return micropostRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    public int countByUser(String userId) {
        return micropostRepository.countByUserId(userId);
    }

    public Page<MicropostResponseDto> getFeed(String userId, Pageable pageable) {
        return micropostRepository.findFeed(userId, pageable)
                .map(m -> new MicropostResponseDto(
                        m.getId(),
                        m.getContent(),
                        m.getCreatedAt(),
                        new UserSummaryDto(
                                m.getUser().getId(),
                                m.getUser().getName(),
                                m.getUser().getEmail()
                        )
                ));
    }

    @Transactional
    public boolean delete(Long id, String userId) {
        Optional<Micropost> micropostOpt = micropostRepository.findById(id);
        if (micropostOpt.isPresent()) {
            Micropost micropost = micropostOpt.get();
            if (!micropost.getUser().getId().equals(userId)) {
                return false; // không cho phép xóa nếu không phải chủ post
            }
            if (micropost.getPicture() != null) {
                try {
                    Files.deleteIfExists(uploadPath.resolve(micropost.getPicture()));
                } catch (IOException e) {
                    // Log error nếu cần
                }
            }
            micropostRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
