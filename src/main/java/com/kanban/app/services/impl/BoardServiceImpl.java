package com.kanban.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

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

    private BoardDTO toDTO(Board board) {
        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setName(board.getName());
        dto.setDescription(board.getDescription());
        dto.setCreatedAt(board.getCreatedAt());
        if (board.getCreatedBy() != null) dto.setCreatedBy(new EntityIdentifier(board.getCreatedBy().getId()));
        if (board.getProject() != null) dto.setProject(new EntityIdentifier(board.getProject().getId()));
        // Buckets omitted for simplicity
        return dto;
    }

    private Board toEntity(BoardDTO dto) {
        Board entity = new Board();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        // createdBy, project, buckets omitted for simplicity
        return entity;
    }
}
