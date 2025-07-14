package com.kanban.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.TaskDTO;
import com.kanban.app.models.entities.Task;
import com.kanban.app.repositories.TaskRepository;
import com.kanban.app.services.interfaces.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDTO findById(Long id) {
        return taskRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO save(TaskDTO dto) {
        Task entity = toEntity(dto);
        return toDTO(taskRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setPriority(task.getPriority());
        dto.setStartDate(task.getStartDate());
        dto.setDescription(task.getDescription());
        if (task.getCreatedBy() != null) dto.setCreatedBy(new EntityIdentifier(task.getCreatedBy().getId()));
        if (task.getBucket() != null) dto.setBucket(new EntityIdentifier(task.getBucket().getId()));
        // Subtasks omitted for simplicity
        return dto;
    }

    private Task toEntity(TaskDTO dto) {
        Task entity = new Task();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setPriority(dto.getPriority());
        entity.setStartDate(dto.getStartDate());
        entity.setDescription(dto.getDescription());
        // createdBy, bucket, subtasks omitted for simplicity
        return entity;
    }
}
