package com.tasklist.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasklist.tasklist.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompleted(boolean completed);
     List<Task> findByCompletedFalse();   // fetch only incomplete tasks
}
