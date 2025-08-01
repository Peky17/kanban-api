package com.kanban.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.kanban.app.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.ProjectDTO;
import com.kanban.app.models.entities.Project;
import com.kanban.app.repositories.ProjectRepository;
import com.kanban.app.services.interfaces.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    AuthService authService;

    @Override
    public ProjectDTO findById(Long id) {
        return projectRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return projectRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO save(ProjectDTO dto) {
        Project entity = toEntity(dto);
        return toDTO(projectRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    private ProjectDTO toDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setStartDate(project.getStartDate());
        dto.setDueDate(project.getDueDate());
        // User identity relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        Long userId = project.getCreatedBy().getId();
        userIdentity.setId(userId);
        dto.setCreatedBy(userIdentity);
        // Boards omitted for simplicity
        return dto;
    }

    private Project toEntity(ProjectDTO dto) {
        Project entity = new Project();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setStartDate(dto.getStartDate());
        entity.setDueDate(dto.getDueDate());
        // User relationship (set the user in session)
        entity.setCreatedBy(authService.getCurrentUser());
        // boards omitted for simplicity
        return entity;
    }
}
