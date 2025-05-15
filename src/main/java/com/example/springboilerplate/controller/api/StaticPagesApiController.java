package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.security.UserPrincipal;
import com.example.springboilerplate.service.MicropostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaticPagesApiController {

    private final MicropostService micropostService;

    @GetMapping("")
    public ResponseEntity<Page<Micropost>> home(@AuthenticationPrincipal UserPrincipal currentUser,
                                               @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(micropostService.getFeed(currentUser.getId(), PageRequest.of(page, 5)));
    }
}
