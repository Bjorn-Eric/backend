package org.example.eric.controller.task;

import jakarta.validation.Valid;
import org.example.eric.dto.TaskDTO;
import org.example.eric.model.Task;
import org.example.eric.model.User;
import org.example.eric.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    private final TaskService taskService;

    @Autowired
    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id, @AuthenticationPrincipal User user) throws AccessDeniedException {
        Task task = taskService.findByTaskId(id, user.getId());
        return new TaskDTO(task);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTaskById(@PathVariable Long id, @AuthenticationPrincipal User user, @Valid @RequestBody TaskDTO taskDTO) throws AccessDeniedException {
        System.out.println("TaskRestController.updateTaskById");
        Task task = taskService.updateTaskById(taskDTO, user.getId());
        return new TaskDTO(task);
    }

    @PostMapping("/new")
    public TaskDTO createTask(@AuthenticationPrincipal User user, @Valid @RequestBody TaskDTO taskDTO) {
        System.out.println("=====================================");
        System.out.println("TaskRestController.createTask");
        System.out.println("taskDTO = " + taskDTO);
        System.out.println("=====================================");
        Task task = taskService.createTask(taskDTO, user);
        return new TaskDTO(task);
    }
}
