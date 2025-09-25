package com.tasklist.tasklist.service;

import com.tasklist.tasklist.model.Task;
import com.tasklist.tasklist.repository.TaskRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public Task markCompleted(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(true);
        return taskRepository.save(task);
    }

public Task getTaskById(Long id) {
    return taskRepository.findById(id).orElse(null);
}
public List<Task> getIncompleteTasks() {
        return taskRepository.findByCompletedFalse();
    }
}

