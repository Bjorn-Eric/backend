package org.example.eric.service;

import org.example.eric.dto.TaskDTO;
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

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllByUser(User user) {
        return taskRepository.findAllByUser(user);
    }

    public List<Task> getAllByTodaysDate(User user) {
        LocalDate date = LocalDate.now();
        return taskRepository.findAllByUserAndDueDate(user, date);
    }

    public List<Task> getUpcomingTasks(User user) {
        return taskRepository.findAllUpcomingTasks(user);
    }

    public List<Task> getCompletedTasks(User user) {
        return taskRepository.findAllCompletedTasks(user);
    }

    public Task save(TaskDTO task, User user) {
        Task newTask = new Task();

        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setDueDate(task.getDueDate());
        newTask.setCompleted(task.isCompleted());
        newTask.setUser(user);

        return taskRepository.save(newTask);
    }

    public Task findByTaskId(Long id, Long userId) throws AccessDeniedException {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null && task.getUser().getId().equals(userId)) {
            return task;
        }

        throw new AccessDeniedException("You are not the owner of this task!");
    }

    public Task updateTaskById(TaskDTO taskDTO, User user) throws AccessDeniedException {
        Task taskToUpdate = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new AccessDeniedException("Task not found!"));

        if (taskToUpdate.getUser() == null || !taskToUpdate.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not the owner of this task!");
        }

        taskToUpdate.setTitle(taskDTO.getTitle());
        taskToUpdate.setDescription(taskDTO.getDescription());
        taskToUpdate.setDueDate(taskDTO.getDueDate());
        taskToUpdate.setCompleted(taskDTO.isCompleted());

        return taskRepository.save(taskToUpdate);
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
