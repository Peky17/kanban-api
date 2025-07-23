package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByBucketId(Long id);
}
