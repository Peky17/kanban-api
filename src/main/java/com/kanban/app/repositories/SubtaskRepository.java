package com.kanban.app.repositories;

import com.kanban.app.models.entities.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findAllByTaskId(Long id);
}
