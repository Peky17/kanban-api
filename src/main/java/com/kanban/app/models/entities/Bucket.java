package com.kanban.app.models.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private String description;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private Board board;

    @OneToMany(mappedBy = "bucket", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Task> tasks;

    public Bucket() {}
    public Bucket(String name, String color, String description, LocalDate createdAt) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.createdAt = createdAt;
    }
    public Bucket(Long id, String name, String color, String description, LocalDate createdAt, User createdBy, Board board) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.board = board;
        //this.tasks = tasks;
    }
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
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}
