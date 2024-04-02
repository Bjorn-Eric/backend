package org.example.eric.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private boolean completed;

    private LocalDateTime dueDate;

    public Task(String title, String description, boolean completed, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
    }

    public Task() {

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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
