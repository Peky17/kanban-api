package com.kanban.app.controllers;

import com.kanban.app.models.dto.ProjectDTO;
import com.kanban.app.models.dto.SubtaskDTO;
import com.kanban.app.services.interfaces.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/subtasks")
public class SubtaskController {
    @Autowired
    SubtaskService subtaskService;

    @GetMapping
    public List<SubtaskDTO> getAll() {
        return subtaskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubtaskDTO> getById(@PathVariable Long id) {
        SubtaskDTO dto = subtaskService.findById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/subtasks/{taskId}")
    public ResponseEntity<Set<SubtaskDTO>> getSubtasksByTaskId(@PathVariable Long taskId) {
        Set<SubtaskDTO> list = subtaskService.getSubtasksByTask(taskId);
        if (list != null) return ResponseEntity.ok(list);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SubtaskDTO> create(@RequestBody SubtaskDTO dto) {
        return ResponseEntity.ok(subtaskService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubtaskDTO> update(@PathVariable Long id, @RequestBody SubtaskDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(subtaskService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subtaskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
