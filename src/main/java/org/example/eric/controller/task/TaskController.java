package org.example.eric.controller.task;

import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.example.eric.dto.TaskDTO;
import org.example.eric.model.Task;
import org.example.eric.model.User;
import org.example.eric.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/task/new")
    public String renderNewTaskForm(Model model) {
        model.addAttribute("task", new TaskDTO());
        return "new_task";
    }

    @PostMapping("/task/new")
    public String createNewTask(@Valid @ModelAttribute("task") TaskDTO task, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "new_task";
        }

        taskService.save(task, user);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{id}")
    public String renderTaskDetailsPage(Model model, @AuthenticationPrincipal User user, @PathVariable Long id) throws AccessDeniedException {
        Task task = taskService.findByTaskId(id, user.getId());
        model.addAttribute("task", task);
        return "task_details";
    }

    @PostMapping("/task/{id}")
    public String updateTaskWithId(@Valid Task task, @AuthenticationPrincipal User user, BindingResult bindingResult) throws AccessDeniedException {

        if (bindingResult.hasErrors()) {
            return "task_details";
        }

        task.setUser(user);
        taskService.updateTaskById(task, user);

        return "redirect:/tasks";

    }

    @PostMapping("/task/delete")
    public String deleteTaskWithId(@RequestParam Long taskId, @AuthenticationPrincipal User user) throws AccessDeniedException {
        taskService.deleteTask(taskId, user);

        return "redirect:/tasks";
    }
}
