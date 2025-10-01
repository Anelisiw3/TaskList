package com.tasklist.tasklist.service;

import com.tasklist.tasklist.model.Task;
import com.tasklist.tasklist.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // CREATE
    public Task createTask(Task task) {
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("Pending");
        }
        Task savedTask = taskRepository.save(task);
        logger.info("Created new task with ID: {} and Title: {}", savedTask.getId(), savedTask.getTitle());
        return savedTask;
    }

    // READ - all tasks
    public List<Task> getAllTasks() {
        logger.debug("Fetching all tasks");
        return taskRepository.findAll();
    }

    // READ - by status
    public List<Task> getTasksByStatus(String status) {
        logger.debug("Fetching tasks with status: {}", status);
        return taskRepository.findByStatus(status);
    }

    // READ - incomplete (Pending)
    public List<Task> getIncompleteTasks() {
        logger.debug("Fetching all incomplete tasks (Pending)");
        return taskRepository.findByStatus("Pending");
    }

    // READ - by id
    public Task getTaskById(Long id) {
        logger.debug("Fetching task with ID: {}", id);
        return taskRepository.findById(id).orElse(null);
    }

    // UPDATE - mark completed
    public Task markCompleted(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus("Completed");
            Task updatedTask = taskRepository.save(task);
            logger.info("Marked task as completed with ID: {}", id);
            return updatedTask;
        }
        logger.warn("Attempted to mark task as completed, but task with ID: {} was not found", id);
        return null;
    }

    // UPDATE - edit whole task (title, dueDate, status)
    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setTitle(updatedTask.getTitle());
            task.setDueDate(updatedTask.getDueDate());
            task.setStatus(updatedTask.getStatus());
            Task savedTask = taskRepository.save(task);
            logger.info("Updated task with ID: {}", id);
            return savedTask;
        }
        logger.warn("Attempted to update task, but task with ID: {} was not found", id);
        return null;
    }

    // DELETE
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            logger.info("Deleted task with ID: {}", id);
            return true;
        }
        logger.warn("Attempted to delete task, but task with ID: {} was not found", id);
        return false;
    }
}
