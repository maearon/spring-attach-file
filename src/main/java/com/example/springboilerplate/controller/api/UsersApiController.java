package com.example.springboilerplate.controller.api;

// import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.EditResponseDto;
import com.example.springboilerplate.dto.FollowingResponseDto;
import com.example.springboilerplate.dto.MicropostResponseDto;
import com.example.springboilerplate.dto.ShowResponseDto;
import com.example.springboilerplate.dto.UpdateUserRequest;
import com.example.springboilerplate.dto.UpdateUserWrapperRequest;
import com.example.springboilerplate.dto.UserDetailDto;
// import com.example.springboilerplate.dto.UserDto;
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
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
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

    @GetMapping("/edit")
    public ResponseEntity<?> edit(@AuthenticationPrincipal UserPrincipal currentUser) {
        UserSummaryDto userDto = new UserSummaryDto(
            currentUser.getId(),
            currentUser.getName(),
            currentUser.getEmail(),
            currentUser.getGravatar()
            // GravatarUtils.getGravatarUrl(currentUser.getEmail(), 80)
        );

        EditResponseDto response = new EditResponseDto(
            userDto,
            currentUser.getGravatar()
            // GravatarUtils.getGravatarUrl(currentUser.getEmail(), 80)
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

    // @PutMapping("/{id}")
    // public ResponseEntity<?> update(@PathVariable String id, 
    //                                @Valid @RequestBody UserDto userDto,
    //                                @AuthenticationPrincipal User currentUser) {
    //     if (!currentUser.getId().equals(id) && !currentUser.isAdmin()) {
    //         return ResponseEntity.badRequest().body(new ApiResponse(false, "You are not authorized to update this user"));
    //     }

    //     User updatedUser = userService.updateUser(id, userDto.getName(), userDto.getEmail(), userDto.getPassword());
    //     return ResponseEntity.ok(updatedUser);
    // }
    @PatchMapping("/{id}")
    // @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<?> update(
        @PathVariable String id,
        @Valid @RequestBody UpdateUserWrapperRequest wrapper,
        @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        UpdateUserRequest request = wrapper.getUser();

        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(403).body(Map.of("error", "Bạn không có quyền cập nhật người dùng này."));
            //return ResponseEntity.badRequest().body(new ApiResponse(false, "You are not authorized to access this user"));
        }

        // if (!currentUser.getId().equals(id) && !currentUser.isAdmin()) {
        //     return ResponseEntity.badRequest().body(new ApiResponse(false, "You are not authorized to update this user"));
        // }

        User user = optionalUser.get();

        // Xử lý cập nhật thông tin cá nhân (name, email)
        boolean profileUpdated = userService.updateProfile(user, request);
        if (!profileUpdated) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email đã được sử dụng."));
        }

        // Xử lý đổi mật khẩu nếu có nhập
        boolean hasPassword = StringUtils.hasText(request.getPassword());
        boolean hasConfirm = StringUtils.hasText(request.getPassword_confirmation());

        if (hasPassword || hasConfirm) {
            if (!hasPassword || !hasConfirm || !request.getPassword().equals(request.getPassword_confirmation())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Mật khẩu xác nhận không khớp."));
            }

            boolean passwordChanged = userService.changePassword(user, request.getPassword());
            if (!passwordChanged) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Cập nhật mật khẩu thất bại."));
            }
        }

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "flash_success", List.of("Success", "Cập nhật thành công.")
        ));
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> destroy(@PathVariable String id, @AuthenticationPrincipal User currentUser) {
    //     if (!currentUser.isAdmin()) {
    //         return ResponseEntity.badRequest().body(new ApiResponse(false, "You are not authorized to delete users"));
    //     }

    //     boolean deleted = userService.deleteUser(id);
    //     if (deleted) {
    //         return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
    //     } else {
    //         return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete user"));
    //     }
    // }
    @DeleteMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id, @AuthenticationPrincipal UserPrincipal currentUser) {
        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete user"));
        }

        return ResponseEntity.ok(Map.of(
                "flash", List.of("Success", "User deleted successfully")
        ));
    }
}
