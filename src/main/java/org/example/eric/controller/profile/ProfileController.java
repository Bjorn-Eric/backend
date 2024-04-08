package org.example.eric.controller.profile;

import jakarta.validation.Valid;
import org.example.eric.controller.profile.dto.ChangePasswordFormDTO;
import org.example.eric.model.User;
import org.example.eric.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Controller
public class ProfileController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String renderProfilePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("passwordForm", new ChangePasswordFormDTO());
        return "profile";
    }

    @PostMapping("/profile")
    public String modifyUserPassword(@Valid ChangePasswordFormDTO passwordFormDTO, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding errors: " + bindingResult.getAllErrors());
            model.addAttribute("user", user);
            return "profile";
        }

        if (!passwordFormDTO.getNewPassword().equals(passwordFormDTO.getNewRetypedPassword())) {
            bindingResult.rejectValue("newRetypedPassword", "mismatch.password", "Passwords do not match");
        }

        if (!passwordEncoder.matches(passwordFormDTO.getOldPassword(), user.getPassword())) {
            bindingResult.rejectValue("oldPassword", "incorrect.password", "Incorrect password");
            model.addAttribute("user", user);
            return "profile";
        }

        userDetailsService.changePassword(user, passwordFormDTO.getNewPassword());
        model.addAttribute("user", user);
        model.addAttribute("successMessage", "Password updated successfully!");
        return "profile";
    }

    @GetMapping("profile/{id}")
    public String renderProfilePageBasedOnId(@AuthenticationPrincipal User user, @PathVariable Long id, Model model) {
        try {

            if (Objects.equals(user.getId(), id)) {
                return "redirect:/profile";
            }

            User userDetails = userDetailsService.getUserDetailsById(user, id);
            model.addAttribute("user", userDetails);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "profile";
    }
}
