package com.kanban.app.repositories;

import com.kanban.app.models.entities.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    List<ProjectUser> findAllByUserId(Long userId);
    List<ProjectUser> findAllByProjectId(Long projectId);
}
