package com.tasklist.tasklist.service;

import com.tasklist.tasklist.model.Task;
import com.tasklist.tasklist.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // CREATE
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // READ - all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // READ - by status
    public List<Task> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    // READ - incomplete
    public List<Task> getIncompleteTasks() {
        return taskRepository.findByCompleted(false);
    }

    // READ - by id
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // UPDATE - mark completed
    public Task markCompleted(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setCompleted(true);
            return taskRepository.save(task);
        }
        return null;
    }

    // UPDATE edit whole task (title, dueDate, completed)
    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setTitle(updatedTask.getTitle());
            task.setDueDate(updatedTask.getDueDate());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        }
        return null;
    }

    
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
