package com.kanban.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kanban.app.models.entities.Project;
import com.kanban.app.models.entities.User;
import com.kanban.app.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.BoardDTO;
import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.entities.Board;
import com.kanban.app.repositories.BoardRepository;
import com.kanban.app.services.interfaces.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private AuthService authService;

    @Override
    public BoardDTO findById(Long id) {
        return boardRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public BoardDTO save(BoardDTO dto) {
        Board entity = toEntity(dto);
        return toDTO(boardRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Set<BoardDTO> getBoardsByProjectId(Long id){
        List<Board> boardList = boardRepository.findAllByProjectId(id);
        Set<BoardDTO> boardSet = new HashSet<>();
        for(Board board : boardList){
            BoardDTO dto = new BoardDTO();
            dto.setId(board.getId());
            dto.setName(board.getName());
            dto.setDescription(board.getDescription());
            dto.setCreatedAt(board.getCreatedAt());
            // Project associated
            EntityIdentifier projectIdentity = new EntityIdentifier();
            projectIdentity.setId(board.getProject().getId());
            dto.setProject(projectIdentity);
            // User associated
            EntityIdentifier userIdentity = new EntityIdentifier();
            userIdentity.setId(board.getCreatedBy().getId());
            dto.setCreatedBy(userIdentity);
            boardSet.add(dto);
        }
        return boardSet;
    }

    private BoardDTO toDTO(Board board) {
        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setName(board.getName());
        dto.setDescription(board.getDescription());
        dto.setCreatedAt(board.getCreatedAt());
        // Project relationship
        EntityIdentifier projectIdentity = new EntityIdentifier();
        projectIdentity.setId(board.getProject().getId());
        dto.setProject(projectIdentity);
        // User identity relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        Long userId = board.getCreatedBy().getId();
        userIdentity.setId(userId);
        dto.setCreatedBy(userIdentity);
        return dto;
    }

    private Board toEntity(BoardDTO dto) {
        Board entity = new Board();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        // Project relationship
        Long projectId = dto.getProject().getId();
        Project project = new Project();
        project.setId(projectId);
        entity.setProject(project);
        // User relationship (set the user in session)
        entity.setCreatedBy(authService.getCurrentUser());
        return entity;
    }
}
