package com.kanban.app.models.dto;

public class EntityIdentifier {
    private Long id;

    public EntityIdentifier() {}

    public EntityIdentifier(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
