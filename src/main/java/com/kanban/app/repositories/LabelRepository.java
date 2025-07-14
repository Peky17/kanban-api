package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
