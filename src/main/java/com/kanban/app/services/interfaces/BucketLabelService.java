package com.kanban.app.services.interfaces;

import java.util.List;

import com.kanban.app.models.dto.BucketLabelDTO;

public interface BucketLabelService {
    BucketLabelDTO findById(Long id);
    List<BucketLabelDTO> findAll();
    BucketLabelDTO save(BucketLabelDTO dto);
    void delete(Long id);
}
