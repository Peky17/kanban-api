package com.kanban.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.BucketDTO;
import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.entities.Bucket;
import com.kanban.app.repositories.BucketRepository;
import com.kanban.app.services.interfaces.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Autowired
    private BucketRepository bucketRepository;

    @Override
    public BucketDTO findById(Long id) {
        return bucketRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<BucketDTO> findAll() {
        return bucketRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public BucketDTO save(BucketDTO dto) {
        Bucket entity = toEntity(dto);
        return toDTO(bucketRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        bucketRepository.deleteById(id);
    }

    private BucketDTO toDTO(Bucket bucket) {
        BucketDTO dto = new BucketDTO();
        dto.setId(bucket.getId());
        dto.setName(bucket.getName());
        dto.setColor(bucket.getColor());
        dto.setDescription(bucket.getDescription());
        dto.setCreatedAt(bucket.getCreatedAt());
        if (bucket.getCreatedBy() != null) dto.setCreatedBy(new EntityIdentifier(bucket.getCreatedBy().getId()));
        if (bucket.getBoard() != null) dto.setBoard(new EntityIdentifier(bucket.getBoard().getId()));
        // Tasks omitted for simplicity
        return dto;
    }

    private Bucket toEntity(BucketDTO dto) {
        Bucket entity = new Bucket();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setColor(dto.getColor());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        // createdBy, board, tasks omitted for simplicity
        return entity;
    }
}
