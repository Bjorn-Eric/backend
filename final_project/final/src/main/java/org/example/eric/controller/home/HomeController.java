package org.example.eric.controller.home;

import org.example.eric.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    TaskService taskService;


    @GetMapping("/home")
    public String renderHomePage(Model model) {
        model.addAttribute("user");
        return "home";
    }
}
