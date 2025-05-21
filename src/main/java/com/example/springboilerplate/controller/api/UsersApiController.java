package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.FollowingResponseDto;
import com.example.springboilerplate.dto.MicropostResponseDto;
import com.example.springboilerplate.dto.ShowResponseDto;
import com.example.springboilerplate.dto.UserDetailDto;
import com.example.springboilerplate.dto.UserDto;
import com.example.springboilerplate.dto.UserShowDto;
import com.example.springboilerplate.dto.UserSummaryDto;
import com.example.springboilerplate.dto.UsersResponseDto;
import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.security.UserPrincipal;
import com.example.springboilerplate.service.ActiveStorageService;
import com.example.springboilerplate.service.MicropostService;
import com.example.springboilerplate.service.RelationshipService;
import com.example.springboilerplate.service.UserService;
import com.example.springboilerplate.utils.GravatarUtils;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersApiController {

    private final UserService userService;
    private final MicropostService micropostService;
    private final RelationshipService relationshipService;
    private final ActiveStorageService attachmentService;

    // @GetMapping
    // public ResponseEntity<Page<User>> index(@RequestParam(defaultValue = "0") int page) {
    //     return ResponseEntity.ok(userService.findAll(PageRequest.of(page, 10)));
    // }
    @GetMapping
    public ResponseEntity<Page<UsersResponseDto>> index(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "0") int page
    ) {
        // if (currentUser == null) {
        //     return ResponseEntity.status(401).body("Unauthorized");
        // }

        int safePage = Math.max(0, page - 1);
        return ResponseEntity.ok(userService.findAll(PageRequest.of(safePage, 5)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable String id,
                                @AuthenticationPrincipal UserPrincipal currentUser) {

        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        // Get microposts list
        Page<Micropost> microposts = micropostService.findByUserId(id, PageRequest.of(0, 5));

        // Đếm follow
        long followingCount = relationshipService.countFollowing(id);
        long followersCount = relationshipService.countFollowers(id);

        // Check is current user following
        boolean isFollowing = false;
        Long idRelationship = null;
        if (currentUser != null) {
            isFollowing = relationshipService.isFollowing(currentUser.getId(), id);
            idRelationship = relationshipService.findRelationshipId(currentUser.getId(), id)
                                                .orElse(null);
        }

        UserShowDto userDto = new UserShowDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getGravatar(),
                followingCount,
                followersCount,
                isFollowing
        );

        List<MicropostResponseDto> micropostDtos = microposts.getContent().stream().map(m -> {
            // String gravatarId = DigestUtils.md5DigestAsHex(m.getUser().getEmail().toLowerCase().getBytes());
            List<String> imageUrls = attachmentService.findAttachments("Micropost", m.getId(), "images");
            return new MicropostResponseDto(
                    m.getId(),
                    m.getContent(),
                    GravatarUtils.getGravatarUrl(m.getUser().getEmail(), 50),
                    m.getCreatedAt(),
                    new UserSummaryDto(
                            m.getUser().getId(),
                            m.getUser().getName(),
                            m.getUser().getEmail(),
                            m.getUser().getGravatar()
                    ),
                    imageUrls // <-- thêm dòng này
            );
        }).toList();

        ShowResponseDto response = new ShowResponseDto(
                idRelationship,
                micropostDtos,
                microposts.getTotalElements(),
                userDto
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<?> following(@PathVariable String id, @RequestParam(defaultValue = "0") int page) {
        // if (currentUser == null) {
        //     return ResponseEntity.status(401).body("Unauthorized");
        // }

        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        int safePage = Math.max(0, page - 1);
        Page<UsersResponseDto> followingPage = userService.getFollowingPaginated(id, PageRequest.of(safePage, 5));
        // List<User> xusers = userService.getXUsers(); // ví dụ: gợi ý bạn bè, người nổi bật, v.v.

        List<UserSummaryDto> users = followingPage.stream().map(u ->
            new UserSummaryDto(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getGravatar()
                // md5Hex(u.getEmail().toLowerCase()),
                // 50
            )
        ).toList();

        List<UserSummaryDto> xuserDtos = followingPage.stream().map(u ->
            new UserSummaryDto(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getGravatar()
                // md5Hex(u.getEmail().toLowerCase()),
                // 30
            )
        ).toList();

        UserDetailDto userDto = new UserDetailDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getFollowing().size(),
            user.getFollowers().size(),
            user.getMicroposts().size()
            // md5Hex(user.getEmail().toLowerCase())
        );

        FollowingResponseDto response = new FollowingResponseDto(
            users,
            (int) followingPage.getTotalElements(),
            xuserDtos,
            userDto
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<?> followers(@PathVariable String id, @RequestParam(defaultValue = "0") int page) {
        // if (currentUser == null) {
        //     return ResponseEntity.status(401).body("Unauthorized");
        // }

        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        int safePage = Math.max(0, page - 1);
        Page<UsersResponseDto> followersPage = userService.getFollowersPaginated(id, PageRequest.of(safePage, 5));
        // List<User> xusers = userService.getXUsers(); // ví dụ: gợi ý bạn bè, người nổi bật, v.v.

        List<UserSummaryDto> users = followersPage.stream().map(u ->
            new UserSummaryDto(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getGravatar()
                // md5Hex(u.getEmail().toLowerCase()),
                // 50
            )
        ).toList();

        List<UserSummaryDto> xuserDtos = followersPage.stream().map(u ->
            new UserSummaryDto(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getGravatar()
                // md5Hex(u.getEmail().toLowerCase()),
                // 30
            )
        ).toList();

        UserDetailDto userDto = new UserDetailDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getFollowing().size(),
            user.getFollowers().size(),
            user.getMicroposts().size()
            // md5Hex(user.getEmail().toLowerCase())
        );

        FollowingResponseDto response = new FollowingResponseDto(
            users,
            (int) followersPage.getTotalElements(),
            xuserDtos,
            userDto
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, 
                                   @Valid @RequestBody UserDto userDto,
                                   @AuthenticationPrincipal User currentUser) {
        if (!currentUser.getId().equals(id) && !currentUser.isAdmin()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "You are not authorized to update this user"));
        }

        User updatedUser = userService.updateUser(id, userDto.getName(), userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable String id, @AuthenticationPrincipal User currentUser) {
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
