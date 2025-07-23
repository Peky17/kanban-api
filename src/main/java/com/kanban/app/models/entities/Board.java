package com.kanban.app.models.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    @JsonBackReference
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference
    private List<Bucket> buckets;

    public Board() {}

    public Board(String name, String description, LocalDate createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Board(Long id, String name, String description, LocalDate createdAt, User createdBy, Project project, List<Bucket> buckets) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.project = project;
        this.buckets = buckets;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public List<Bucket> getBuckets() { return buckets; }
    public void setBuckets(List<Bucket> buckets) { this.buckets = buckets; }
}
