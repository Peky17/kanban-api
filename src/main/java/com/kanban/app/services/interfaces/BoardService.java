package com.kanban.app.services.interfaces;

import java.util.List;
import java.util.Set;

import com.kanban.app.models.dto.BoardDTO;

public interface BoardService {
    BoardDTO findById(Long id);
    List<BoardDTO> findAll();
    BoardDTO save(BoardDTO dto);
    Set<BoardDTO> getBoardsByProjectId(Long id);
    void delete(Long id);
}
