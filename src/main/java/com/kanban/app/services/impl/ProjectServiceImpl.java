package com.kanban.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.BoardDTO;
import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.ProjectDTO;
import com.kanban.app.models.entities.Board;
import com.kanban.app.models.entities.Project;
import com.kanban.app.repositories.ProjectRepository;
import com.kanban.app.services.auth.AuthService;
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
        Project entity;
        boolean isUpdate = dto.getId() != null && projectRepository.existsById(dto.getId());
        if (isUpdate) {
            entity = projectRepository.findById(dto.getId()).orElseThrow();
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setCreatedAt(dto.getCreatedAt());
            entity.setStartDate(dto.getStartDate());
            entity.setDueDate(dto.getDueDate());
            // User relationship (set the user in session)
            entity.setCreatedBy(authService.getCurrentUser());
            // Boards: mantener los existentes, eliminar los que no estén en el DTO, agregar/actualizar los que vienen
            if (dto.getBoards() != null) {
                // Eliminar boards que no están en el DTO
                entity.getBoards().removeIf(board -> dto.getBoards().stream().noneMatch(b -> b.getId() != null && b.getId().equals(board.getId())));
                // Agregar o actualizar boards del DTO
                for (BoardDTO boardDTO : dto.getBoards()) {
                    Board board = null;
                    if (boardDTO.getId() != null) {
                        board = entity.getBoards().stream().filter(b -> b.getId().equals(boardDTO.getId())).findFirst().orElse(null);
                    }
                    if (board == null) {
                        board = new Board();
                        entity.getBoards().add(board);
                    }
                    board.setId(boardDTO.getId());
                    board.setName(boardDTO.getName());
                    board.setDescription(boardDTO.getDescription());
                    board.setCreatedAt(boardDTO.getCreatedAt());
                    board.setProject(entity);
                }
            }
        } else {
            entity = toEntity(dto);
        }
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
        // Boards
        if (project.getBoards() != null) {
            List<BoardDTO> boardDTOs = project.getBoards().stream()
                .map(board -> {
                    BoardDTO boardDTO = new BoardDTO();
                    boardDTO.setId(board.getId());
                    boardDTO.setName(board.getName());
                    boardDTO.setDescription(board.getDescription());
                    boardDTO.setCreatedAt(board.getCreatedAt());
                    // Project y createdBy pueden omitirse o mapearse según necesidad
                    return boardDTO;
                })
                .collect(Collectors.toList());
            dto.setBoards(boardDTOs);
        }
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
        // Boards
        if (dto.getBoards() != null) {
            if (entity.getBoards() == null) {
                entity.setBoards(dto.getBoards().stream().map(boardDTO -> {
                    Board board = new Board();
                    board.setId(boardDTO.getId());
                    board.setName(boardDTO.getName());
                    board.setDescription(boardDTO.getDescription());
                    board.setCreatedAt(boardDTO.getCreatedAt());
                    board.setProject(entity);
                    // createdBy puede omitirse o mapearse según necesidad
                    return board;
                }).collect(Collectors.toList()));
            } else {
                entity.getBoards().clear();
                entity.getBoards().addAll(dto.getBoards().stream().map(boardDTO -> {
                    Board board = new Board();
                    board.setId(boardDTO.getId());
                    board.setName(boardDTO.getName());
                    board.setDescription(boardDTO.getDescription());
                    board.setCreatedAt(boardDTO.getCreatedAt());
                    board.setProject(entity);
                    return board;
                }).collect(Collectors.toList()));
            }
        }
        return entity;
    }
}
