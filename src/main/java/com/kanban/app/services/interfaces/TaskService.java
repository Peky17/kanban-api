package com.kanban.app.services.interfaces;

import java.util.List;

import com.kanban.app.models.dto.TaskDTO;

public interface TaskService {
    TaskDTO findById(Long id);
    List<TaskDTO> findAll();
    TaskDTO save(TaskDTO dto);
    void delete(Long id);
}
