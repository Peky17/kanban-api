package com.kanban.app.models.dto;

import java.time.LocalDate;

public class SubtaskDTO {
    private Long id;
    private String name;
    private LocalDate createdAt;
    private String priority;
    private LocalDate startdDate;
    private LocalDate dueDate;
    private EntityIdentifier createdBy;
    private EntityIdentifier task;

    public SubtaskDTO(){}

    public SubtaskDTO(Long id, String name, LocalDate createdAt, String priority, LocalDate startdDate, LocalDate dueDate, EntityIdentifier createdBy, EntityIdentifier task) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.priority = priority;
        this.startdDate = startdDate;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getStartdDate() {
        return startdDate;
    }

    public void setStartdDate(LocalDate startdDate) {
        this.startdDate = startdDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public EntityIdentifier getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EntityIdentifier createdBy) {
        this.createdBy = createdBy;
    }

    public EntityIdentifier getTask() {
        return task;
    }

    public void setTask(EntityIdentifier task) {
        this.task = task;
    }
}
