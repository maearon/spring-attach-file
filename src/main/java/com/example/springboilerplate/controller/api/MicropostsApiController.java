package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.security.UserPrincipal;
import com.example.springboilerplate.service.MicropostService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/microposts")
@RequiredArgsConstructor
public class MicropostsApiController {

    private final MicropostService micropostService;

    @GetMapping
    public ResponseEntity<Page<Micropost>> index(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(micropostService.findAll(PageRequest.of(page, 10)));
    }

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam("micropost[content]") String content,
            @RequestParam(value = "micropost[image]", required = false) MultipartFile image,
            @RequestParam(value = "micropost[images]", required = false) MultipartFile[] images
    ) throws IOException {
        try {
            // List<ActiveStorageAttachment> images = attachmentRepo.findByRecordTypeAndRecordId("Micropost", post.getId());
            // attachmentService.attachFile(image, "Micropost", currentUser.getId());
            // attachFile(MultipartFile file, String recordType, UUID recordId)


            Micropost micropost = micropostService.create(currentUser.getId(), content, images);

            if (micropost == null || micropost.getId() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", List.of("Failed to create micropost")));
            }

            return ResponseEntity.ok(Map.of(
                "flash", List.of("success", "Micropost created!")
                // "post", micropost
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal currentUser) {
        boolean deleted = micropostService.delete(id, currentUser.getId());
        if (deleted) {
            return ResponseEntity.ok(Map.of("flash", List.of("success", "Micropost deleted")));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", List.of("Failed to delete micropost")));
        }
    }
}
