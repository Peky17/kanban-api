package com.kanban.app.models.dto;

import java.time.LocalDate;
import java.util.List;

public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate startDate;
    private LocalDate dueDate;
    private EntityIdentifier createdBy;
    private List<EntityIdentifier> boards;

    public ProjectDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public EntityIdentifier getCreatedBy() { return createdBy; }
    public void setCreatedBy(EntityIdentifier createdBy) { this.createdBy = createdBy; }
    public List<EntityIdentifier> getBoards() { return boards; }
    public void setBoards(List<EntityIdentifier> boards) { this.boards = boards; }
}
