package org.example.eric.repository;

import org.example.eric.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDueDate(LocalDateTime dueDate);

    List<Task> findByUser_Id(Long userId);
}
