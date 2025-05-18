package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.security.UserPrincipal;
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
    public ResponseEntity<?> follow(@PathVariable String id, @AuthenticationPrincipal UserPrincipal currentUser) {
        relationshipService.follow(currentUser.getId(), id);
        return ResponseEntity.ok(Map.of("follow", "success", "message", "User followed successfully"));
    }

    @DeleteMapping("/{id}/unfollow")
    public ResponseEntity<?> unfollow(@PathVariable String id, @AuthenticationPrincipal UserPrincipal currentUser) {
        relationshipService.unfollow(currentUser.getId(), id);
        return ResponseEntity.ok(Map.of("unfollow", "success", "message", "User unfollowed successfully"));
    }
}
