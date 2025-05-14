package com.example.springboilerplate.controller;

import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.MicropostDto;
import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.MicropostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/microposts")
@RequiredArgsConstructor
public class MicropostsController {

    private final MicropostService micropostService;

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
