package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersApiController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> index(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(userService.findAll(PageRequest.of(page, 10)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<?> following(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(userService.getFollowingPaginated(id, PageRequest.of(page, 10)));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<?> followers(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(userService.getFollowersPaginated(id, PageRequest.of(page, 10)));
    }
}
