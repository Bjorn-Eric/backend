package org.example.eric.controller.task;

import jakarta.validation.Valid;
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
        model.addAttribute("task", new Task());
        return "new_task";
    }

    @PostMapping("/task/new")
    public String createNewTask(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "new_task";
        }

        System.out.println("DEBUG: " + task.toString());

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
    public String updateTaskWithId(@Valid Task task, @AuthenticationPrincipal User user, BindingResult bindingResult, Model model) throws AccessDeniedException {

        if (bindingResult.hasErrors()) {
            return "task_details";
        }

        task.setUser(user);
        Task updatedTask = taskService.updateTaskById(task, user);

        model.addAttribute("task", updatedTask);

        return "task_details";

    }

    @PostMapping("/task/delete")
    public String deleteTaskWithId(@RequestParam Long taskId, @AuthenticationPrincipal User user) throws AccessDeniedException {
        System.out.println("DEBUG_TASK_DELETE");
        taskService.deleteTask(taskId, user);

        return "redirect:/tasks";
    }
}
