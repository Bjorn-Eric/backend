package org.example.eric.controller.users;

import org.example.eric.dto.UserDTO;
import org.example.eric.model.User;
import org.example.eric.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Controller
public class UsersController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String renderUsersPage(Model model) {
            model.addAttribute("users", userDetailsService.getAllUsers());
            return "users";
    }

    @GetMapping("/users/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUserPage(Model model) {
            model.addAttribute("user", new UserDTO());
            return "add_user";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@AuthenticationPrincipal User user, Model model, @RequestParam Long user_id) {
        try {
            userDetailsService.deleteUserById(user_id);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/users";
    }

    @PostMapping("/users/deactivate")
    public String deactivateUser(@AuthenticationPrincipal User user, @RequestParam Long user_id) {
        try {
            System.out.println("user_id: " + user_id);
            int ans = userDetailsService.deactivateUserById(user, user_id);

            System.out.println("Deactivated ans: " + ans);

            if (ans == 1 && Objects.equals(user.getId(), user_id)) {
                return "redirect:/";
            }

            if (ans == 1) {
                return "redirect:/users";
            }

            return "redirect:/error";
        } catch (AccessDeniedException e) {
            return "redirect:/error";
        }
    }

    @PostMapping("/users/activate")
    public String activateUser(@AuthenticationPrincipal User user, @RequestParam Long user_id) {
        try {
            int result = userDetailsService.activateUserById(user_id);

            if (result == 1 && Objects.equals(user.getId(), user_id)) {
                return "redirect:/";
            }

            if (result == 1) {
                return "redirect:/users";
            }

            return "redirect:/error";
        } catch (AccessDeniedException e) {
            return "redirect:/error";
        }
    }

}
