package com.kanban.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.BucketLabelDTO;
import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.entities.BucketLabel;
import com.kanban.app.repositories.BucketLabelRepository;
import com.kanban.app.services.interfaces.BucketLabelService;

@Service
public class BucketLabelServiceImpl implements BucketLabelService {
    @Autowired
    private BucketLabelRepository bucketLabelRepository;

    @Override
    public BucketLabelDTO findById(Long id) {
        return bucketLabelRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<BucketLabelDTO> findAll() {
        return bucketLabelRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public BucketLabelDTO save(BucketLabelDTO dto) {
        BucketLabel entity = toEntity(dto);
        return toDTO(bucketLabelRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        bucketLabelRepository.deleteById(id);
    }

    private BucketLabelDTO toDTO(BucketLabel bucketLabel) {
        BucketLabelDTO dto = new BucketLabelDTO();
        dto.setId(bucketLabel.getId());
        if (bucketLabel.getLabel() != null) dto.setLabel(new EntityIdentifier(bucketLabel.getLabel().getId()));
        if (bucketLabel.getBucket() != null) dto.setBucket(new EntityIdentifier(bucketLabel.getBucket().getId()));
        if (bucketLabel.getCreatedBy() != null) dto.setCreatedBy(new EntityIdentifier(bucketLabel.getCreatedBy().getId()));
        return dto;
    }

    private BucketLabel toEntity(BucketLabelDTO dto) {
        BucketLabel entity = new BucketLabel();
        entity.setId(dto.getId());
        // label, bucket, createdBy omitted for simplicity
        return entity;
    }
}
