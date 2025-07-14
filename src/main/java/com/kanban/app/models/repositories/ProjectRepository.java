package com.kanban.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
