package com.kanban.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.ProjectDTO;
import com.kanban.app.models.dto.ProjectUserDTO;
import com.kanban.app.models.dto.UserDTO;
import com.kanban.app.models.entities.Project;
import com.kanban.app.models.entities.ProjectUser;
import com.kanban.app.models.entities.User;
import com.kanban.app.repositories.ProjectRepository;
import com.kanban.app.repositories.ProjectUserRepository;
import com.kanban.app.services.interfaces.ProjectUserService;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {
    @Autowired
    private ProjectUserRepository projectUserRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private com.kanban.app.repositories.UserRepository userRepository;

    @Override
    public ProjectUserDTO findById(Long id) {
        return projectUserRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<ProjectUserDTO> findAll() {
        return projectUserRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectUserDTO save(ProjectUserDTO dto) {
        ProjectUser entity = toEntity(dto);
        return toDTO(projectUserRepository.save(entity));
    }

    @Override
    public Set<ProjectDTO> findProjectsByUserId(Long id){
        List<ProjectUser> projectsUser = projectUserRepository.findAllByUserId(id);
        Set<ProjectDTO> newProjectList = new HashSet<>();
        for(ProjectUser projectUser: projectsUser){
            ProjectDTO dto = new ProjectDTO();
            Project entity = projectUser.getProject();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setStartDate(entity.getStartDate());
            dto.setDueDate(entity.getDueDate());
            dto.setCreatedAt(entity.getCreatedAt());
            EntityIdentifier userIdentity = new EntityIdentifier();
            userIdentity.setId(entity.getCreatedBy().getId());
            dto.setCreatedBy(userIdentity);
            newProjectList.add(dto);
        }
        return newProjectList;
    }

    @Override
    public Set<UserDTO> findUsersByProjectId(Long id){
        List<ProjectUser> usersProject = projectUserRepository.findAllByProjectId(id);
        Set<UserDTO> newUserSet = new HashSet<>();
        for(ProjectUser projectUser : usersProject){
            UserDTO dto = new UserDTO();
            User entity = projectUser.getUser();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setEmail(entity.getEmail());
            dto.setCellphone(entity.getCellphone());
            EntityIdentifier roleIdentity = new EntityIdentifier();
            roleIdentity.setId(entity.getRole().getId());
            dto.setRole(roleIdentity);
            dto.setPassword(entity.getPassword());
            newUserSet.add(dto);
        }
        return newUserSet;
    }

    @Override
    public void delete(Long id) {
        projectUserRepository.deleteById(id);
    }

    private ProjectUserDTO toDTO(ProjectUser projectUser) {
        ProjectUserDTO dto = new ProjectUserDTO();
        dto.setId(projectUser.getId());
        // project relationship
        EntityIdentifier projectIdentity = new EntityIdentifier();
        Long projectId = projectUser.getProject().getId();
        projectIdentity.setId(projectId);
        dto.setProject(projectIdentity);
        // user relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        Long userId = projectUser.getUser().getId();
        userIdentity.setId(userId);
        dto.setUser(userIdentity);
        return dto;
    }

    private ProjectUser toEntity(ProjectUserDTO dto) {
        ProjectUser entity = new ProjectUser();
        entity.setId(dto.getId());
        // project relationship
        Long projectId = dto.getProject().getId();
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        entity.setProject(project);
        // User relationship
        Long userId = dto.getUser().getId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        entity.setUser(user);
        return entity;
    }
}
