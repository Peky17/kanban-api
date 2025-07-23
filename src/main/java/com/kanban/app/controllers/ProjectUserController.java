package com.kanban.app.controllers;

import com.kanban.app.models.dto.ProjectDTO;
import com.kanban.app.models.dto.ProjectUserDTO;
import com.kanban.app.models.dto.UserDTO;
import com.kanban.app.services.impl.ProjectUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/project-users")
public class ProjectUserController {
    @Autowired
    private ProjectUserServiceImpl projectUserService;

    @GetMapping
    public List<ProjectUserDTO> getAll() {
        return projectUserService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectUserDTO> getById(@PathVariable Long id) {
        ProjectUserDTO dto = projectUserService.findById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/projects/{userId}")
    public ResponseEntity<Set<ProjectDTO>> getProjectsByUserId(@PathVariable Long userId) {
        Set<ProjectDTO> list = projectUserService.findProjectsByUserId(userId);
        if (list != null) return ResponseEntity.ok(list);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users/{projectId}")
    public ResponseEntity<Set<UserDTO>> getUsersByProjectId(@PathVariable Long projectId) {
        Set<UserDTO> list = projectUserService.findUsersByProjectId(projectId);
        if (list != null) return ResponseEntity.ok(list);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProjectUserDTO> create(@RequestBody ProjectUserDTO dto) {
        return ResponseEntity.ok(projectUserService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectUserDTO> update(@PathVariable Long id, @RequestBody ProjectUserDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(projectUserService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
