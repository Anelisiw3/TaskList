package com.tasklist.tasklist.controller;

import com.tasklist.tasklist.model.Task;
import com.tasklist.tasklist.service.TaskService;

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
}
