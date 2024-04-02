package org.example.eric.controller.signup;

import jakarta.validation.Valid;
import org.example.eric.controller.signup.dto.SignupFormDTO;
import org.example.eric.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/register")
    public String renderRegisterPage(Model model) {
        model.addAttribute("user", new SignupFormDTO());
        return "signup_form";
    }

    @GetMapping("/signup_success")
    public String renderSignupSuccessPage() {
        return "signup_success";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute SignupFormDTO dto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            model.addAttribute("user", dto);
            return "signup_form";
        }

        boolean ans = registrationService.createUser(dto);

        if (!ans) {
            return "error";
        }

        return "redirect:/";
    }
}
