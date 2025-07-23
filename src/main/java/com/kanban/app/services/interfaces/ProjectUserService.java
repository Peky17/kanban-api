package com.kanban.app.services.interfaces;

import com.kanban.app.models.dto.ProjectDTO;
import com.kanban.app.models.dto.ProjectUserDTO;
import com.kanban.app.models.dto.UserDTO;

import java.util.List;
import java.util.Set;

public interface ProjectUserService {
    ProjectUserDTO findById(Long id);
    List<ProjectUserDTO> findAll();
    ProjectUserDTO save(ProjectUserDTO dto);
    void delete(Long id);
    Set<ProjectDTO> findProjectsByUserId(Long id);
    Set<UserDTO> findUsersByProjectId(Long id);
}
