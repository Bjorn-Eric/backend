package org.example.eric.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.eric.dto.TaskDTO;

import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, min = 3, message = "Title length must be between 3 and 100 characters")
    private String title;

    @Size(max = 1000, message = "You exceeded the description length")
    private String description;
    private boolean completed;

    private LocalDate dueDate;

    public Task(String title, String description, boolean completed, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
    }

    public Task() {

    }

    public Task(TaskDTO taskDTO, User user) {
        this.id = taskDTO.getId();
        this.title = taskDTO.getTitle();
        this.description = taskDTO.getDescription();
        this.completed = taskDTO.isCompleted();
        this.dueDate = taskDTO.getDueDate();
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", dueDate=" + dueDate +
                ", user=" + user +
                '}';
    }
}