package com.kanban.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kanban.app.models.entities.Board;
import com.kanban.app.models.entities.Bucket;
import com.kanban.app.models.entities.Label;
import com.kanban.app.services.auth.AuthService;
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
    @Autowired
    private AuthService authService;

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

    @Override
    public Set<BucketLabelDTO> getLabelsByBucketId(Long id){
        List<BucketLabel> bucketLabelList = bucketLabelRepository.findAllByBucketId(id);
        Set<BucketLabelDTO> bucketLabelSet = new HashSet<>();
        for(BucketLabel bucketLabel : bucketLabelList){
            BucketLabelDTO dto = new BucketLabelDTO();
            dto.setId(bucketLabel.getId());
            // label relationship
            EntityIdentifier labelIdentity = new EntityIdentifier();
            labelIdentity.setId(bucketLabel.getLabel().getId());
            dto.setLabel(labelIdentity);
            // bucket relationship
            EntityIdentifier bucketIdentity = new EntityIdentifier();
            bucketIdentity.setId(bucketLabel.getBucket().getId());
            dto.setBucket(bucketIdentity);
            // user relationship
            EntityIdentifier userIdentity = new EntityIdentifier();
            userIdentity.setId(bucketLabel.getCreatedBy().getId());
            dto.setCreatedBy(userIdentity);

            bucketLabelSet.add(dto);
        }
        return bucketLabelSet;
    }

    private BucketLabelDTO toDTO(BucketLabel bucketLabel) {
        BucketLabelDTO dto = new BucketLabelDTO();
        dto.setId(bucketLabel.getId());
        // User identity relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        Long userId = bucketLabel.getCreatedBy().getId();
        userIdentity.setId(userId);
        dto.setCreatedBy(userIdentity);
        // Bucket identity
        EntityIdentifier bucketIdentity = new EntityIdentifier();
        Long bucketId = bucketLabel.getBucket().getId();
        bucketIdentity.setId(bucketId);
        dto.setBucket(bucketIdentity);
        // Label Identity
        EntityIdentifier labelIdentity = new EntityIdentifier();
        Long labelId = bucketLabel.getLabel().getId();
        labelIdentity.setId(labelId);
        dto.setLabel(labelIdentity);
        return dto;
    }

    private BucketLabel toEntity(BucketLabelDTO dto) {
        BucketLabel entity = new BucketLabel();
        entity.setId(dto.getId());
        // User relationship (set the user in session)
        entity.setCreatedBy(authService.getCurrentUser());
        // Bucket relationship
        Bucket bucket = new Bucket();
        Long bucketId = dto.getBucket().getId();
        bucket.setId(bucketId);
        entity.setBucket(bucket);
        // Label relationship
        Label label = new Label();
        Long labelId = dto.getLabel().getId();
        label.setId(labelId);
        entity.setLabel(label);
        return entity;
    }
}
