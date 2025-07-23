package com.kanban.app.controllers;

import java.util.List;
import java.util.Set;

import com.kanban.app.models.dto.BucketDTO;
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

import com.kanban.app.models.dto.BucketLabelDTO;
import com.kanban.app.services.interfaces.BucketLabelService;

@RestController
@RequestMapping("/api/v1/bucket-labels")
public class BucketLabelController {
    @Autowired
    private BucketLabelService bucketLabelService;

    @GetMapping
    public List<BucketLabelDTO> getAll() {
        return bucketLabelService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BucketLabelDTO> getById(@PathVariable Long id) {
        BucketLabelDTO dto = bucketLabelService.findById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/labels/{bucketId}")
    public ResponseEntity<Set<BucketLabelDTO>> getLabelsByBucketId(@PathVariable Long bucketId) {
        Set<BucketLabelDTO> list = bucketLabelService.getLabelsByBucketId(bucketId);
        if (list != null) return ResponseEntity.ok(list);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BucketLabelDTO> create(@RequestBody BucketLabelDTO dto) {
        return ResponseEntity.ok(bucketLabelService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BucketLabelDTO> update(@PathVariable Long id, @RequestBody BucketLabelDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(bucketLabelService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bucketLabelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
