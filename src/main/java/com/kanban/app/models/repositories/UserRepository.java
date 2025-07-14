package com.kanban.app.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
