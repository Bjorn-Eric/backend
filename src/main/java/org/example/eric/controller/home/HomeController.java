package org.example.eric.controller.home;

import org.example.eric.model.User;
import org.example.eric.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    TaskService taskService;


    @GetMapping("/home")
    public String renderHomePage(Model model, @AuthenticationPrincipal User user) {
        System.out.println(user.toString());
        model.addAttribute("user", user);
        model.addAttribute("tasks_today", taskService.getAllByTodaysDate(user));
        model.addAttribute("tasks_upcoming", taskService.getUpcomingTasks(user));
        model.addAttribute("tasks_completed", taskService.getCompletedTasks(user));

        return "home";
    }
}
