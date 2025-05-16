package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.MicropostResponseDto;
import com.example.springboilerplate.dto.ShowResponseDto;
import com.example.springboilerplate.dto.UserDto;
import com.example.springboilerplate.dto.UserShowDto;
import com.example.springboilerplate.dto.UserSummaryDto;
import com.example.springboilerplate.dto.UsersResponseDto;
import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.security.UserPrincipal;
import com.example.springboilerplate.service.MicropostService;
import com.example.springboilerplate.service.RelationshipService;
import com.example.springboilerplate.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
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

    // @GetMapping("/{id}")
    // public ResponseEntity<User> show(@PathVariable Long id) {
    //     return userService.findById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }
    // @GetMapping("/{id}")
    // public String show(@PathVariable Long id, Model model, @AuthenticationPrincipal UserPrincipal currentUser) {
    //     return userService.findById(id).map(user -> {
    //         model.addAttribute("user", user);
    //         Page<Micropost> microposts = micropostService.findByUserId(id, PageRequest.of(0, 5));
    //         model.addAttribute("microposts", microposts);
            
    //         if (currentUser != null) {
    //             model.addAttribute("following", relationshipService.isFollowing(currentUser.getId(), id));
    //         }
            
    //         model.addAttribute("followingCount", relationshipService.countFollowing(id));
    //         model.addAttribute("followersCount", relationshipService.countFollowers(id));
            
    //         return "users/show";
    //     }).orElse("redirect:/");
    // }
    // @GetMapping("/{id}")
    // public ResponseEntity<?> show(@PathVariable Long id,
    //                             @AuthenticationPrincipal UserPrincipal currentUser) {

    //     return userService.findById(id).map(user -> {
    //         // Lấy danh sách microposts
    //         Page<Micropost> microposts = micropostService.findByUserId(id, PageRequest.of(0, 5));

    //         // Đếm follow
    //         long followingCount = relationshipService.countFollowing(id);
    //         long followersCount = relationshipService.countFollowers(id);

    //         // Kiểm tra current user có đang follow không
    //         boolean isFollowing = false;
    //         Long idRelationship = null;
    //         if (currentUser != null) {
    //             isFollowing = relationshipService.isFollowing(currentUser.getId(), id);
    //             idRelationship = relationshipService.findRelationshipId(currentUser.getId(), id)
    //                                                 .orElse(null); // thêm hàm này trong service
    //         }

    //         // Build UserShowDto
    //         UserShowDto userDto = new UserShowDto(
    //                 user.getId(),
    //                 user.getName(),
    //                 user.getEmail(),
    //                 followingCount,
    //                 followersCount,
    //                 isFollowing
    //         );

    //         // Build danh sách MicropostResponseDto
    //         List<MicropostResponseDto> micropostDtos = microposts.getContent().stream().map(m -> {
    //             String gravatarId = DigestUtils.md5DigestAsHex(m.getUser().getEmail().toLowerCase().getBytes());
    //             return new MicropostResponseDto(
    //                     m.getId(),
    //                     m.getContent(),
    //                     m.getCreatedAt(),
    //                     new UserSummaryDto(
    //                             m.getUser().getId(),
    //                             m.getUser().getName(),
    //                             m.getUser().getEmail()
    //                     )
    //             );
    //         }).toList();

    //         // Trả về response DTO
    //         ShowResponseDto response = new ShowResponseDto(
    //                 idRelationship,
    //                 micropostDtos,
    //                 microposts.getTotalElements(),
    //                 userDto
    //         );

    //         return ResponseEntity.ok(response);
    //     }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    // }
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id,
                                @AuthenticationPrincipal UserPrincipal currentUser) {

        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        // Lấy danh sách microposts
        Page<Micropost> microposts = micropostService.findByUserId(id, PageRequest.of(0, 5));

        // Đếm follow
        long followingCount = relationshipService.countFollowing(id);
        long followersCount = relationshipService.countFollowers(id);

        // Kiểm tra current user có đang follow không
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
                followingCount,
                followersCount,
                isFollowing
        );

        List<MicropostResponseDto> micropostDtos = microposts.getContent().stream().map(m -> {
            String gravatarId = DigestUtils.md5DigestAsHex(m.getUser().getEmail().toLowerCase().getBytes());
            return new MicropostResponseDto(
                    m.getId(),
                    m.getContent(),
                    m.getCreatedAt(),
                    new UserSummaryDto(
                            m.getUser().getId(),
                            m.getUser().getName(),
                            m.getUser().getEmail()
                    )
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
