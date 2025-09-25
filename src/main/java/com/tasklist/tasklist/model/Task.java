package com.tasklist.tasklist.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "task")  // explicitly map to the "task" table
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "due_date") // map snake_case column → camelCase field
    private LocalDate dueDate;

    private boolean completed = false;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
