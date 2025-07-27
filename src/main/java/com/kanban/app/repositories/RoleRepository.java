package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
