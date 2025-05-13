package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.UserDto;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, 
                                   @Valid @RequestBody UserDto userDto,
                                   @AuthenticationPrincipal User currentUser) {
        if (!currentUser.getId().equals(id) && !currentUser.isAdmin()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "You are not authorized to update this user"));
        }

        User updatedUser = userService.updateUser(id, userDto.getName(), userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        if (!currentUser.isAdmin()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "You are not authorized to delete users"));
        }

        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete user"));
        }
    }
}
