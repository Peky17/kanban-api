package com.kanban.app.services.interfaces;

import java.util.List;
import java.util.Set;

import com.kanban.app.models.dto.BucketDTO;

public interface BucketService {
    BucketDTO findById(Long id);
    List<BucketDTO> findAll();
    BucketDTO save(BucketDTO dto);
    Set<BucketDTO> getBucketsByBoardId(Long id);
    void delete(Long id);
}
