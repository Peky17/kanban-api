package com.kanban.app.services.interfaces;

import com.kanban.app.models.dto.TaskDTO;
import com.kanban.app.models.dto.UserDTO;
import com.kanban.app.models.dto.UserTaskDTO;

import java.util.List;
import java.util.Set;

public interface UserTaskService {
    UserTaskDTO findById(Long id);
    List<UserTaskDTO> findAll();
    UserTaskDTO save(UserTaskDTO dto);
    void delete(Long id);
    Set<UserDTO> getUsersByTaskId(Long id);
    Set<TaskDTO> getTasksByUserId(Long id);
}
