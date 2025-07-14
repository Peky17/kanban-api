package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
