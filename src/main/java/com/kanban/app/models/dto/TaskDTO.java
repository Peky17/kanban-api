package com.kanban.app.models.dto;

import java.time.LocalDate;
import java.util.List;

public class TaskDTO {
    private Long id;
    private String name;
    private LocalDate createdAt;
    private String priority;
    private LocalDate startDate;
    private String description;
    private EntityIdentifier createdBy;
    private EntityIdentifier bucket;
    private List<EntityIdentifier> subtasks;

    public TaskDTO() {}

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
    public EntityIdentifier getCreatedBy() { return createdBy; }
    public void setCreatedBy(EntityIdentifier createdBy) { this.createdBy = createdBy; }
    public EntityIdentifier getBucket() { return bucket; }
    public void setBucket(EntityIdentifier bucket) { this.bucket = bucket; }
    public List<EntityIdentifier> getSubtasks() { return subtasks; }
    public void setSubtasks(List<EntityIdentifier> subtasks) { this.subtasks = subtasks; }
}
