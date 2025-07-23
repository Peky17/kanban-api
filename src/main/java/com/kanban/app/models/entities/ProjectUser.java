package com.kanban.app.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name="project_user")
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ProjectUser(){}

    public ProjectUser(Project project, User user) {
        this.project = project;
        this.user = user;
    }

    public ProjectUser(Long id, Project project, User user) {
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
