package com.kanban.app.services.interfaces;

import java.util.List;
import java.util.Set;

import com.kanban.app.models.dto.BucketLabelDTO;

public interface BucketLabelService {
    BucketLabelDTO findById(Long id);
    List<BucketLabelDTO> findAll();
    BucketLabelDTO save(BucketLabelDTO dto);
    Set<BucketLabelDTO> getLabelsByBucketId(Long id);
    void delete(Long id);
}
