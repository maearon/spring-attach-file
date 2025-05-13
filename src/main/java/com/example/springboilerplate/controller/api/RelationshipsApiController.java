package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/relationships")
@RequiredArgsConstructor
public class RelationshipsApiController {

    private final RelationshipService relationshipService;

    @PostMapping("/{id}/follow")
    public ResponseEntity<?> follow(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        relationshipService.follow(currentUser.getId(), id);
        return ResponseEntity.ok(Map.of("status", "success", "message", "User followed successfully"));
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<?> unfollow(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        relationshipService.unfollow(currentUser.getId(), id);
        return ResponseEntity.ok(Map.of("status", "success", "message", "User unfollowed successfully"));
    }
}
