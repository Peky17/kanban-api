package com.kanban.app.services.interfaces;

import java.util.List;

import com.kanban.app.models.dto.ProjectDTO;

public interface ProjectService {
    ProjectDTO findById(Long id);
    List<ProjectDTO> findAll();
    ProjectDTO save(ProjectDTO dto);
    void delete(Long id);
}
