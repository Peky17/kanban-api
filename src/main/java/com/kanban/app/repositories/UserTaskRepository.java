package com.kanban.app.repositories;

import com.kanban.app.models.dto.UserTaskDTO;
import com.kanban.app.models.entities.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    List<UserTask> getAllByUserId(Long id);
    List<UserTask> getAllByTaskId(Long id);
}
