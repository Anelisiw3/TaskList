package com.tasklist.tasklist.controller;

import com.tasklist.tasklist.model.Task;
import com.tasklist.tasklist.service.TaskService;

import org.springframework.http.ResponseEntity; //  Add this
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/incomplete")
    public List<Task> getIncompleteTasks() {
        return taskService.getIncompleteTasks();
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) Boolean completed) {
        if (completed != null) {
            return taskService.getTasksByStatus(completed);
        }
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}/complete")
    public Task completeTask(@PathVariable Long id) {
        return taskService.markCompleted(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }
}
