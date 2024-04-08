package org.example.eric.repository;

import org.example.eric.model.Task;
import org.example.eric.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser(User user);

    List<Task> findAllByUserAndDueDate(User user, LocalDate date);

    @Query("SELECT t from Task t WHERE t.user = :user AND t.dueDate > CURRENT_TIMESTAMP AND t.completed = false")
    List<Task> findAllUpcomingTasks(User user);

    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.dueDate < CURRENT_TIMESTAMP AND t.completed = false")
    List<Task> findAllPastTasks(User user);

    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.completed = true")
    List<Task> findAllCompletedTasks(User user);
}