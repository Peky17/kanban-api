package com.kanban.app.services.interfaces;

import com.kanban.app.models.dto.SubtaskDTO;

import java.util.List;
import java.util.Set;

public interface SubtaskService {
    SubtaskDTO findById(Long id);
    List<SubtaskDTO> findAll();
    SubtaskDTO save(SubtaskDTO dto);
    void delete(Long id);
    Set<SubtaskDTO> getSubtasksByTask(Long id);
}
