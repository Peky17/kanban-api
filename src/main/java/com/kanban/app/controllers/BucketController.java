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

import com.kanban.app.models.dto.BucketDTO;
import com.kanban.app.services.interfaces.BucketService;

@RestController
@RequestMapping("/api/v1/buckets")
public class BucketController {
    @Autowired
    private BucketService bucketService;

    @GetMapping
    public List<BucketDTO> getAll() {
        return bucketService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BucketDTO> getById(@PathVariable Long id) {
        BucketDTO dto = bucketService.findById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BucketDTO> create(@RequestBody BucketDTO dto) {
        return ResponseEntity.ok(bucketService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BucketDTO> update(@PathVariable Long id, @RequestBody BucketDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(bucketService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bucketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
