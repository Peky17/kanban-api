package com.kanban.app.models.dto;

public class ProjectUserDTO {
    private Long id;
    private EntityIdentifier project;
    private EntityIdentifier user;
    public ProjectUserDTO(){}

    public ProjectUserDTO(Long id, EntityIdentifier project, EntityIdentifier user) {
        this.id = id;
        this.project = project;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityIdentifier getProject() {
        return project;
    }

    public void setProject(EntityIdentifier project) {
        this.project = project;
    }

    public EntityIdentifier getUser() {
        return user;
    }

    public void setUser(EntityIdentifier user) {
        this.user = user;
    }
}
