package com.kanban.app.services.interfaces;

import java.util.List;

import com.kanban.app.models.dto.LabelDTO;

public interface LabelService {
    LabelDTO findById(Long id);
    List<LabelDTO> findAll();
    LabelDTO save(LabelDTO dto);
    void delete(Long id);
}
