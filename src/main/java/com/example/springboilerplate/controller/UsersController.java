package com.example.springboilerplate.controller;

import com.example.springboilerplate.dto.UsersResponseDto;
import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.MicropostService;
import com.example.springboilerplate.service.RelationshipService;
import com.example.springboilerplate.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final MicropostService micropostService;
    private final RelationshipService relationshipService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<UsersResponseDto> users = userService.findAll(PageRequest.of(page, 10));
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable String id, Model model, @AuthenticationPrincipal User currentUser) {
        return userService.findById(id).map(user -> {
            model.addAttribute("user", user);
            Page<Micropost> microposts = micropostService.findByUserId(id, PageRequest.of(0, 10));
            model.addAttribute("microposts", microposts);
            
            if (currentUser != null) {
                model.addAttribute("following", relationshipService.isFollowing(currentUser.getId(), id));
            }
            
            model.addAttribute("followingCount", relationshipService.countFollowing(id));
            model.addAttribute("followersCount", relationshipService.countFollowers(id));
            
            return "users/show";
        }).orElse("redirect:/");
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/new";
        }
        
        userService.create(user);
        return "redirect:/login?activated";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public String edit(@PathVariable String id, Model model) {
        return userService.findById(id).map(user -> {
            model.addAttribute("user", user);
            return "users/edit";
        }).orElse("redirect:/");
    }

    @PostMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public String update(@PathVariable String id, @Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/edit";
        }
        
        return userService.findById(id).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            if (!user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }
            userService.update(existingUser);
            return "redirect:/users/" + id;
        }).orElse("redirect:/");
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable String id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/following")
    public String following(@PathVariable String id, Model model, @RequestParam(defaultValue = "0") int page) {
        return userService.findById(id).map(user -> {
            model.addAttribute("user", user);
            model.addAttribute("users", userService.getFollowingPaginated(id, PageRequest.of(page, 10)));
            model.addAttribute("title", "Following");
            model.addAttribute("followingCount", relationshipService.countFollowing(id));
            model.addAttribute("followersCount", relationshipService.countFollowers(id));
            return "users/show_follow";
        }).orElse("redirect:/");
    }

    @GetMapping("/{id}/followers")
    public String followers(@PathVariable String id, Model model, @RequestParam(defaultValue = "0") int page) {
        return userService.findById(id).map(user -> {
            model.addAttribute("user", user);
            model.addAttribute("users", userService.getFollowersPaginated(id, PageRequest.of(page, 10)));
            model.addAttribute("title", "Followers");
            model.addAttribute("followingCount", relationshipService.countFollowing(id));
            model.addAttribute("followersCount", relationshipService.countFollowers(id));
            return "users/show_follow";
        }).orElse("redirect:/");
    }
}
