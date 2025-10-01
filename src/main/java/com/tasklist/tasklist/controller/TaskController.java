package com.tasklist.tasklist.controller;

import com.tasklist.tasklist.model.Task;
import com.tasklist.tasklist.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        logger.info("Received request to create a new task: {}", task.getTitle());
        return taskService.createTask(task);
    }

    // READ - all tasks (optional filter by status)
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String status) {
        if (status != null) {
            logger.info("Received request to fetch tasks with status: {}", status);
            return taskService.getTasksByStatus(status);
        }
        logger.info("Received request to fetch all tasks");
        return taskService.getAllTasks();
    }

    // READ - incomplete tasks only (Pending)
    @GetMapping("/incomplete")
    public List<Task> getIncompleteTasks() {
        logger.info("Received request to fetch incomplete tasks");
        return taskService.getIncompleteTasks();
    }

    // READ - task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        logger.info("Received request to fetch task by ID: {}", id);
        Task task = taskService.getTaskById(id);
        if (task == null) {
            logger.warn("Task with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    
    @PutMapping("/{id}/complete")
    public Task completeTask(@PathVariable Long id) {
        logger.info("Received request to mark task as completed, ID: {}", id);
        return taskService.markCompleted(id);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        logger.info("Received request to update task with ID: {}", id);
        Task task = taskService.updateTask(id, updatedTask);
        if (task == null) {
            logger.warn("Task with ID {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        logger.info("Received request to delete task with ID: {}", id);
        boolean deleted = taskService.deleteTask(id);
        if (!deleted) {
            logger.warn("Task with ID {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
