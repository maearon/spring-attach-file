package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.MicropostResponseDto;
import com.example.springboilerplate.security.UserPrincipal;
import com.example.springboilerplate.service.RelationshipService;
import com.example.springboilerplate.service.MicropostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaticPagesApiController {

    private final MicropostService micropostService;
    private final RelationshipService relationshipService;

    @GetMapping("/feed")
    public ResponseEntity<Page<MicropostResponseDto>> feed1(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "0") int page
    ) {
        // if (currentUser == null) {
        //     return ResponseEntity.status(401).body("Unauthorized");
        // }

        int safePage = Math.max(0, page - 1);
        return ResponseEntity.ok(
                micropostService.getFeed(currentUser.getId(), PageRequest.of(safePage, 5))
        );

    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> feed(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "1") int page // Rails thường bắt đầu từ 1
    ) {
        int safePage = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(safePage, 5);

        Page<MicropostResponseDto> feedItems = micropostService.getFeed(currentUser.getId(), pageable);
        // long micropostCount = micropostService.countByUser(currentUser.getId());
        // long followingCount = followService.countFollowing(currentUser.getId());
        long followingCount = relationshipService.countFollowing(currentUser.getId());
        long followersCount = relationshipService.countFollowers(currentUser.getId());
        long micropostCount = micropostService.countByUser(currentUser.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("feedItems", feedItems);
        response.put("micropost", micropostCount);
        response.put("following", followingCount);
        response.put("followers", followersCount);

        return ResponseEntity.ok(response);
    }
}
