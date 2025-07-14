package com.kanban.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.LabelDTO;
import com.kanban.app.models.entities.Label;
import com.kanban.app.repositories.LabelRepository;
import com.kanban.app.services.interfaces.LabelService;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelRepository labelRepository;

    @Override
    public LabelDTO findById(Long id) {
        return labelRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<LabelDTO> findAll() {
        return labelRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public LabelDTO save(LabelDTO dto) {
        Label entity = toEntity(dto);
        return toDTO(labelRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        labelRepository.deleteById(id);
    }

    private LabelDTO toDTO(Label label) {
        LabelDTO dto = new LabelDTO();
        dto.setId(label.getId());
        dto.setText(label.getText());
        dto.setColor(label.getColor());
        return dto;
    }

    private Label toEntity(LabelDTO dto) {
        Label entity = new Label();
        entity.setId(dto.getId());
        entity.setText(dto.getText());
        entity.setColor(dto.getColor());
        return entity;
    }
}
