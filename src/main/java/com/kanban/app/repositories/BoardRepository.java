package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
