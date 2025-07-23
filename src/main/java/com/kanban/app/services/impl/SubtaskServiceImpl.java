package com.kanban.app.services.impl;

import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.SubtaskDTO;
import com.kanban.app.models.entities.Subtask;
import com.kanban.app.models.entities.Task;
import com.kanban.app.repositories.SubtaskRepository;
import com.kanban.app.services.auth.AuthService;
import com.kanban.app.services.interfaces.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubtaskServiceImpl implements SubtaskService {

    @Autowired
    SubtaskRepository subtaskRepository;
    @Autowired
    private AuthService authService;

    @Override
    public SubtaskDTO findById(Long id) {
        return subtaskRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<SubtaskDTO> findAll() {
        return subtaskRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SubtaskDTO save(SubtaskDTO dto) {
        Subtask entity = toEntity(dto);
        return toDTO(subtaskRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        subtaskRepository.deleteById(id);
    }

    @Override
    public Set<SubtaskDTO> getSubtasksByTask(Long id){
        List<Subtask> subtasksFound = subtaskRepository.findAllByTaskId(id);
        Set<SubtaskDTO> subtasks = new HashSet<>();
        for(Subtask subtask : subtasksFound){
            SubtaskDTO dto = new SubtaskDTO();
            dto.setId(subtask.getId());
            dto.setName(subtask.getName());
            dto.setPriority(subtask.getPriority());
            dto.setStartdDate(subtask.getStartdDate());
            dto.setDueDate(subtask.getDueDate());
            dto.setCreatedAt(subtask.getCreatedAt());
            EntityIdentifier userIdentity = new EntityIdentifier();
            userIdentity.setId(subtask.getCreatedBy().getId());
            dto.setCreatedBy(userIdentity);
            EntityIdentifier taskIdentity = new EntityIdentifier();
            taskIdentity.setId(subtask.getTask().getId());
            dto.setTask(taskIdentity);
            subtasks.add(dto);
        }
        return subtasks;
    }

    private SubtaskDTO toDTO(Subtask subtask) {
        SubtaskDTO dto = new SubtaskDTO();
        dto.setId(subtask.getId());
        dto.setName(subtask.getName());
        dto.setCreatedAt(subtask.getCreatedAt());
        dto.setPriority(subtask.getPriority());
        dto.setStartdDate(subtask.getStartdDate());
        dto.setDueDate(subtask.getDueDate());
        // Bucket relationship
        EntityIdentifier taskIdentity = new EntityIdentifier();
        taskIdentity.setId(subtask.getTask().getId());
        dto.setTask(taskIdentity);
        // User identity relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        Long userId = subtask.getCreatedBy().getId();
        userIdentity.setId(userId);
        dto.setCreatedBy(userIdentity);
        // Subtasks omitted for simplicity
        return dto;
    }

    private Subtask toEntity(SubtaskDTO dto) {
        Subtask entity = new Subtask();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setPriority(dto.getPriority());
        entity.setStartdDate(dto.getStartdDate());
        entity.setDueDate(dto.getDueDate());
        // User relationship (set the user in session)
        entity.setCreatedBy(authService.getCurrentUser());
        // task relationship
        Task task = new Task();
        task.setId(dto.getTask().getId());
        entity.setTask(task);
        // subtasks omitted for simplicity
        return entity;
    }
}
