package com.kanban.app.models.dto;

public class LabelDTO {
    private Long id;
    private String text;
    private String color;

    public LabelDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
