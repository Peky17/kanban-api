package com.kanban.app.services.interfaces;

import java.util.List;

import com.kanban.app.models.dto.BoardDTO;

public interface BoardService {
    BoardDTO findById(Long id);
    List<BoardDTO> findAll();
    BoardDTO save(BoardDTO dto);
    void delete(Long id);
}
