package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByProjectId(Long id);
}
