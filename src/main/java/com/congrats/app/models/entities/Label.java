package com.congrats.app.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String color;

    public Label() {}
    public Label(String text, String color) {
        this.text = text;
        this.color = color;
    }
    public Label(Long id, String text, String color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
