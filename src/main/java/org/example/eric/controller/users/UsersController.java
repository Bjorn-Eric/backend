package org.example.eric.controller.users;

import org.example.eric.model.User;
import org.example.eric.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String renderUsersPage(@AuthenticationPrincipal User user, Model model) {
        if (user.getRole() == User.Role.ROLE_ADMIN) {
            model.addAttribute("users", userDetailsService.getAllUsers());
            return "users";
        }

        return "redirect:/home";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@AuthenticationPrincipal User user, Model model, @RequestParam Long user_id) {
        try {
            userDetailsService.deleteUserById(user, user_id);
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
            System.out.println("user_id: " + user_id);
            int ans = userDetailsService.activateUserById(user, user_id);

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

}
