package com.example.springboilerplate.controller;

import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.MicropostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/microposts")
@RequiredArgsConstructor
public class MicropostsController {

    private final MicropostService micropostService;

    @PostMapping
    public String create(@Valid @ModelAttribute Micropost micropost, BindingResult result,
                         @RequestParam(required = false) MultipartFile picture,
                         @AuthenticationPrincipal User currentUser,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "The form contains errors.");
            return "redirect:/";
        }
        
        micropost.setUser(currentUser);
        micropostService.create(micropost, picture);
        redirectAttributes.addFlashAttribute("success", "Micropost created!");
        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("@micropostService.findById(#id).orElse(new com.example.springboilerplate.model.Micropost()).user.id == authentication.principal.id or hasRole('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        micropostService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Micropost deleted");
        return "redirect:/";
    }
}
