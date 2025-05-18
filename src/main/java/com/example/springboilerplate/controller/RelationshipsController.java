package com.example.springboilerplate.controller;

import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/relationships")
@RequiredArgsConstructor
public class RelationshipsController {

    private final RelationshipService relationshipService;

    @PostMapping("/{id}/follow")
    public String follow(@PathVariable String id, @AuthenticationPrincipal User currentUser,
                         RedirectAttributes redirectAttributes) {
        relationshipService.follow(currentUser.getId(), id);
        redirectAttributes.addFlashAttribute("success", "You are now following this user.");
        return "redirect:/users/" + id;
    }

    @PostMapping("/{id}/unfollow")
    public String unfollow(@PathVariable String id, @AuthenticationPrincipal User currentUser,
                           RedirectAttributes redirectAttributes) {
        relationshipService.unfollow(currentUser.getId(), id);
        redirectAttributes.addFlashAttribute("success", "You have unfollowed this user.");
        return "redirect:/users/" + id;
    }
}
