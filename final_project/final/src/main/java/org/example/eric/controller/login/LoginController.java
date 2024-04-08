package org.example.eric.controller.login;

import org.example.eric.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String renderLoginPage(@AuthenticationPrincipal User user) {

        if (user != null && user.getActive()) {
            return "redirect:/home";
        }

        return "login";
    }
}
