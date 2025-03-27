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
import java.util.logging.Logger;

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
    public String createNewTask(@Valid @ModelAttribute("task") TaskDTO task,BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("task", task);
            return "new_task";
        }

        taskService.createTask(task, user);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{id}")
    public String renderTaskDetailsPage(Model model, @AuthenticationPrincipal User user, @PathVariable Long id) throws AccessDeniedException {
        Task task = taskService.findByTaskId(id, user.getId());
        TaskDTO taskDTO = new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.isCompleted());
        System.out.println("TASK DTO" + taskDTO.toString());
        model.addAttribute("task", taskDTO);

        return "task_details";
    }

    @PostMapping("/task/{id}")
    public String updateTaskWithId(@Valid TaskDTO task, @AuthenticationPrincipal User user, BindingResult bindingResult) throws AccessDeniedException {

        if (bindingResult.hasErrors()) {
            return "task_details";
        }

        taskService.updateTaskById(task, user.getId());

        return "redirect:/tasks";

    }

    @PostMapping("/task/delete")
    public String deleteTaskWithId(@RequestParam Long taskId, @AuthenticationPrincipal User user) throws AccessDeniedException {
        taskService.deleteTask(taskId, user);

        return "redirect:/tasks";
    }
}
