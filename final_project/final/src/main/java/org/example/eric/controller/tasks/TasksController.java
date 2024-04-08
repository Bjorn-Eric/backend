package org.example.eric.controller.tasks;

import org.example.eric.model.Task;
import org.example.eric.model.User;
import org.example.eric.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TasksController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public String renderUserTasks(Model model, @AuthenticationPrincipal User user) {
        List<Task> tasks = taskService.findAllByUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

//    @GetMapping("/task/{id}")
//    public String taskDetails(@PathVariable Long id, Model model) {
//        Optional<Task> = taskService
//    }
}
