package com.kanban.app.models.dto;

public class UserTaskDTO {
    private Long id;
    private boolean isCompleted;
    private EntityIdentifier user;
    private EntityIdentifier task;
    public UserTaskDTO(){}
    public UserTaskDTO(Long id, boolean isCompleted, EntityIdentifier user, EntityIdentifier task) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.user = user;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public EntityIdentifier getUser() {
        return user;
    }

    public void setUser(EntityIdentifier user) {
        this.user = user;
    }

    public EntityIdentifier getTask() {
        return task;
    }

    public void setTask(EntityIdentifier task) {
        this.task = task;
    }
}
