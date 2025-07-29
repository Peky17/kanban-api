package com.kanban.app.models.dto;

import java.time.LocalDate;
import java.util.List;

public class BucketDTO {
    private Long id;
    private String name;
    private String color;
    private String description;
    private LocalDate createdAt;
    private EntityIdentifier createdBy;
    private EntityIdentifier board;
    private List<TaskDTO> tasks;

    public BucketDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    public EntityIdentifier getCreatedBy() { return createdBy; }
    public void setCreatedBy(EntityIdentifier createdBy) { this.createdBy = createdBy; }
    public EntityIdentifier getBoard() { return board; }
    public void setBoard(EntityIdentifier board) { this.board = board; }
    public List<TaskDTO> getTasks() { return tasks; }
    public void setTasks(List<TaskDTO> tasks) { this.tasks = tasks; }
}
