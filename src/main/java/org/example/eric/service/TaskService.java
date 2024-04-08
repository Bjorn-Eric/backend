package org.example.eric.service;

import org.example.eric.model.Task;
import org.example.eric.model.User;
import org.example.eric.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<Task> findAllByUser(User user) {
        return taskRepository.findAllByUser(user);
    }

    public List<Task> getAllByTodaysDate(User user) {
        LocalDate date = LocalDate.now();
        List<Task> tasks = taskRepository.findAllByUserAndDueDate(user, date);
        System.out.println("Tasks:" + tasks.toString());
        return tasks;
    }

    public List<Task> getUpcomingTasks(User user) {
        return taskRepository.findAllUpcomingTasks(user);
    }

    public List<Task> getCompletedTasks(User user) {
        return taskRepository.findAllCompletedTasks(user);
    }

    public Task save(Task task, User user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task findByTaskId(Long id, Long userId) throws AccessDeniedException {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null && task.getUser().getId().equals(userId)) {
            return task;
        }

        throw new AccessDeniedException("You are not the owner of this task!");
    }

    public Task updateTaskById(Task task, User user) throws AccessDeniedException {
        if (user.getId().equals(task.getUser().getId())) {
            return taskRepository.save(task);
        }

        throw new AccessDeniedException("You are not the owner of this task!");
    }

    public void deleteTask(Long id, User user) throws AccessDeniedException {
        System.out.println("TASK_SERVICE");
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null && task.getUser().getId().equals(user.getId())) {
            taskRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("You are not the owner of this task!");
        }
    }
}
