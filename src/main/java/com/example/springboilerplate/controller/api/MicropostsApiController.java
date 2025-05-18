package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.security.UserPrincipal;
import com.example.springboilerplate.service.MicropostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/{id}")
    public ResponseEntity<Micropost> show(@PathVariable Long id) {
        return micropostService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam("micropost[content]") String content,
            @RequestParam("micropost[image]") MultipartFile image
    ) throws IOException {
        try {
            Micropost micropost = micropostService.create(currentUser.getId(), content, image);

            if (micropost == null || micropost.getId() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", List.of("Failed to create micropost")));
            }

            return ResponseEntity.ok().body(Map.of("flash", List.of("success", "Micropost created!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", List.of(e.getMessage())));
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
