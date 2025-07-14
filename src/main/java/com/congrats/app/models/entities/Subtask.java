package com.congrats.app.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate createdAt;
    private String priority;
    private LocalDate startdDate;
    private LocalDate dueDate;

    @OneToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Subtask() {}
    public Subtask(String name, LocalDate createdAt, String priority, LocalDate startdDate, LocalDate dueDate) {
        this.name = name;
        this.createdAt = createdAt;
        this.priority = priority;
        this.startdDate = startdDate;
        this.dueDate = dueDate;
    }
    public Subtask(Long id, String name, LocalDate createdAt, String priority, LocalDate startdDate, LocalDate dueDate, User createdBy, Task task) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.priority = priority;
        this.startdDate = startdDate;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.task = task;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public LocalDate getStartdDate() { return startdDate; }
    public void setStartdDate(LocalDate startdDate) { this.startdDate = startdDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
}
