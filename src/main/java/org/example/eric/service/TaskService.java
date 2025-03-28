package org.example.eric.service;

import org.example.eric.dto.TaskDTO;
import org.example.eric.model.Task;
import org.example.eric.model.User;
import org.example.eric.repository.TaskRepository;
import org.example.eric.utils.Utils;
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

    public List<TaskDTO> findAllByUser(User user) {
        List<Task> tasks = taskRepository.findAllByUser(user);
        return Utils.convertTaskListToTaskDTOList(tasks);
    }

    public List<TaskDTO> getAllByTodaysDate(User user) {
        LocalDate date = LocalDate.now();
        List<Task> tasks = taskRepository.findAllByUserAndDueDate(user, date);
        return Utils.convertTaskListToTaskDTOList(tasks);
    }

    public List<TaskDTO> getUpcomingTasks(User user) {
        List<Task> tasks = taskRepository.findAllUpcomingTasks(user);
        return Utils.convertTaskListToTaskDTOList(tasks);
    }

    public List<TaskDTO> getCompletedTasks(User user) {
        List<Task> tasks = taskRepository.findAllCompletedTasks(user);
        return Utils.convertTaskListToTaskDTOList(tasks);
    }

    public Task createTask(TaskDTO taskDTO, User user) {
        Task newTask = new Task(taskDTO, user);
        return taskRepository.save(newTask);
    }

    public Task findByTaskId(Long id, Long userId) throws AccessDeniedException {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null && task.getUser().getId().equals(userId)) {
            return task;
        }

        throw new AccessDeniedException("You are not the owner of this task!");
    }

    public Task updateTaskById(TaskDTO taskDTO, Long userId) throws AccessDeniedException {
        Task taskToUpdate = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new AccessDeniedException("Task not found!"));

        if (!taskToUpdate.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not the owner of this task!");
        }

        taskToUpdate.setTitle(taskDTO.getTitle());
        taskToUpdate.setDescription(taskDTO.getDescription());
        taskToUpdate.setDueDate(taskDTO.getDueDate());
        taskToUpdate.setCompleted(taskDTO.isCompleted());

        return taskRepository.save(taskToUpdate);
    }


    public void deleteTask(Long id, User user) throws AccessDeniedException {
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null) {
            throw new AccessDeniedException("Task not found!");
        }

        if (task.getUser().getId().equals(user.getId())) {
            taskRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("You are not the owner of this task!");
        }
    }
}
