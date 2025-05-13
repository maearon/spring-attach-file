package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.MicropostDto;
import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.MicropostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

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

    @GetMapping("/feed")
    public ResponseEntity<Page<Micropost>> feed(@AuthenticationPrincipal User currentUser,
                                               @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(micropostService.getFeed(currentUser.getId(), PageRequest.of(page, 10)));
    }

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal User currentUser,
                                   @Valid @RequestPart("micropost") MicropostDto micropostDto,
                                   @RequestPart(value = "picture", required = false) MultipartFile picture) throws IOException {
        Micropost micropost = micropostService.create(currentUser.getId(), micropostDto.getContent(), picture);
        return ResponseEntity.ok(micropost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        boolean deleted = micropostService.delete(id, currentUser.getId());
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse(true, "Micropost deleted successfully"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete micropost"));
        }
    }
}
