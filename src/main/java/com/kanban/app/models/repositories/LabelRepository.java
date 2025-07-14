package com.kanban.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
