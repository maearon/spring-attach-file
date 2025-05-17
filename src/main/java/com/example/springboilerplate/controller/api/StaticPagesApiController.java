package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.MicropostResponseDto;
import com.example.springboilerplate.security.UserPrincipal;
import com.example.springboilerplate.service.MicropostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaticPagesApiController {

    private final MicropostService micropostService;

    @GetMapping("")
    public ResponseEntity<Page<MicropostResponseDto>> feed(
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
}
