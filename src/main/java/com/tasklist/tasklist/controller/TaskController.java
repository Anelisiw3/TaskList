package com.tasklist.tasklist.controller;

import com.tasklist.tasklist.model.Task;
import com.tasklist.tasklist.service.TaskService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // READ - all tasks (optional filter by status)
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String status) {
        if (status != null) {
            return taskService.getTasksByStatus(status);
        }
        return taskService.getAllTasks();
    }

    // READ - incomplete tasks only (Pending)
    @GetMapping("/incomplete")
    public List<Task> getIncompleteTasks() {
        return taskService.getIncompleteTasks();
    }

    // READ - task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    // UPDATE - mark task as Completed
    @PutMapping("/{id}/complete")
    public Task completeTask(@PathVariable Long id) {
        return taskService.markCompleted(id);
    }

    // UPDATE - edit task (title, dueDate, status)
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(id, updatedTask);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
