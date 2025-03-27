package org.example.eric.controller.tasks;
import jakarta.validation.Valid;
import org.example.eric.dto.TaskDTO;
import org.example.eric.model.Task;
import org.example.eric.model.User;
import org.example.eric.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TasksRestController {

    private final TaskService taskService;

    @Autowired
    public TasksRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public List<TaskDTO> getUserTasks(@AuthenticationPrincipal User user)  {
        return taskService.findAllByUser(user);
    }
}

