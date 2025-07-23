package com.kanban.app.services.interfaces;

import java.util.List;
import java.util.Set;

import com.kanban.app.models.dto.TaskDTO;

public interface TaskService {
    TaskDTO findById(Long id);
    List<TaskDTO> findAll();
    TaskDTO save(TaskDTO dto);
    Set<TaskDTO> getAllTasksByBucketId(Long id);
    void delete(Long id);
}
