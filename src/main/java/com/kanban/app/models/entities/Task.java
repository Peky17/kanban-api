package com.kanban.app.models.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate createdAt;
    private String priority;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Subtask> subtasks;

    public Task() {}
    public Task(String name, LocalDate createdAt, String priority, LocalDate startDate, LocalDate dueDate, String description) {
        this.name = name;
        this.createdAt = createdAt;
        this.priority = priority;
        this.startDate = startDate;
        this.description = description;
        this.dueDate = dueDate;
    }
    public Task(Long id, String name, LocalDate createdAt, String priority, LocalDate startDate, LocalDate dueDate, String description, User createdBy, Bucket bucket, List<Subtask> subtasks) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.priority = priority;
        this.startDate = startDate;
        this.description = description;
        this.createdBy = createdBy;
        this.bucket = bucket;
        this.subtasks = subtasks;
        this.dueDate = dueDate;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public Bucket getBucket() { return bucket; }
    public void setBucket(Bucket bucket) { this.bucket = bucket; }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<Subtask> getSubtasks() { return subtasks; }
    public void setSubtasks(List<Subtask> subtasks) {
        if (this.subtasks == null) {
            this.subtasks = subtasks;
        } else {
            this.subtasks.clear();
            if (subtasks != null) {
                this.subtasks.addAll(subtasks);
            }
        }
    }
}
