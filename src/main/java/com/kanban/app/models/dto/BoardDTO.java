package com.kanban.app.models.dto;

import java.time.LocalDate;
import java.util.List;

public class BoardDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate createdAt;
    private EntityIdentifier createdBy;
    private EntityIdentifier project;

    public BoardDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    public EntityIdentifier getCreatedBy() { return createdBy; }
    public void setCreatedBy(EntityIdentifier createdBy) { this.createdBy = createdBy; }
    public EntityIdentifier getProject() { return project; }
    public void setProject(EntityIdentifier project) { this.project = project; }
}
