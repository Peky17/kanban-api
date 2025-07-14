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

import com.kanban.app.models.dto.LabelDTO;
import com.kanban.app.services.interfaces.LabelService;

@RestController
@RequestMapping("/api/v1/labels")
public class LabelController {
    @Autowired
    private LabelService labelService;

    @GetMapping
    public List<LabelDTO> getAll() {
        return labelService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelDTO> getById(@PathVariable Long id) {
        LabelDTO dto = labelService.findById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<LabelDTO> create(@RequestBody LabelDTO dto) {
        return ResponseEntity.ok(labelService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelDTO> update(@PathVariable Long id, @RequestBody LabelDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(labelService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
