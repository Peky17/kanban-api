package com.kanban.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanban.app.models.dto.BoardDTO;
import com.kanban.app.services.interfaces.BoardService;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping
    public List<BoardDTO> getAll() {
        return boardService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getById(@PathVariable Long id) {
        BoardDTO dto = boardService.findById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BoardDTO> create(@RequestBody BoardDTO dto) {
        return ResponseEntity.ok(boardService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> update(@PathVariable Long id, @RequestBody BoardDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(boardService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
