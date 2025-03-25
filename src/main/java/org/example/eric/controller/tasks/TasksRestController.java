package org.example.eric.controller.tasks;
import org.example.eric.model.Task;
import org.example.eric.model.User;
import org.example.eric.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.UnsupportedAudioFileException;
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
    public List<Task> getUserTasks(@RequestHeader("API-Key") String apiKey, @AuthenticationPrincipal User user) throws UnsupportedAudioFileException {
        if(!isValidApiKey(apiKey)){
            throw new UnsupportedAudioFileException("Invalid API Key");
        }

        return taskService.findAllByUser(user);
    }

    private boolean isValidApiKey(String apiKey) {
        return apiKey != null && !apiKey.isEmpty();
    }
}

