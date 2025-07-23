package com.kanban.app.controllers;

import com.kanban.app.models.dto.ProjectDTO;
import com.kanban.app.models.dto.TaskDTO;
import com.kanban.app.models.dto.UserDTO;
import com.kanban.app.models.dto.UserTaskDTO;
import com.kanban.app.services.impl.UserTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user-tasks")
public class UserTaskController {
    @Autowired
    private UserTaskServiceImpl taskService;

    @GetMapping
    public List<UserTaskDTO> getAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTaskDTO> getById(@PathVariable Long id) {
        UserTaskDTO dto = taskService.findById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users/{taskId}")
    public ResponseEntity<Set<UserDTO>> getUsersByTaskId(@PathVariable Long taskId) {
        Set<UserDTO> list = taskService.getUsersByTaskId(taskId);
        if (list != null) return ResponseEntity.ok(list);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tasks/{userId}")
    public ResponseEntity<Set<TaskDTO>> getTasksByUserId(@PathVariable Long userId) {
        Set<TaskDTO> list = taskService.getTasksByUserId(userId);
        if (list != null) return ResponseEntity.ok(list);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserTaskDTO> create(@RequestBody UserTaskDTO dto) {
        return ResponseEntity.ok(taskService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTaskDTO> update(@PathVariable Long id, @RequestBody UserTaskDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(taskService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
