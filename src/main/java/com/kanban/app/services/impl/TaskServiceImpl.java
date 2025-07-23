package com.kanban.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kanban.app.models.entities.Bucket;
import com.kanban.app.services.auth.AuthService;
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
    @Autowired
    private AuthService authService;

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

    @Override
    public Set<TaskDTO> getAllTasksByBucketId(Long id) {
        List<Task> taskList = taskRepository.findAllByBucketId(id);
        Set<TaskDTO> taskSet = new HashSet<>();
        for(Task task : taskList){
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setName(task.getName());
            dto.setDescription(task.getDescription());
            dto.setPriority(task.getPriority());
            dto.setStartDate(task.getStartDate());
            dto.setDueDate(task.getDueDate());
            dto.setCreatedAt(task.getCreatedAt());
            // user association
            EntityIdentifier userIdentity = new EntityIdentifier();
            userIdentity.setId(task.getCreatedBy().getId());
            dto.setCreatedBy(userIdentity);
            // bucket association
            EntityIdentifier bucketIdentity = new EntityIdentifier();
            bucketIdentity.setId(task.getBucket().getId());
            dto.setBucket(bucketIdentity);
            taskSet.add(dto);
        }
        return taskSet;
    }

    private TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setPriority(task.getPriority());
        dto.setStartDate(task.getStartDate());
        dto.setDueDate(task.getDueDate());
        dto.setDescription(task.getDescription());
        // Bucket relationship
        EntityIdentifier bucketIdentity = new EntityIdentifier();
        bucketIdentity.setId(task.getBucket().getId());
        dto.setBucket(bucketIdentity);
        // User identity relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        Long userId = task.getCreatedBy().getId();
        userIdentity.setId(userId);
        dto.setCreatedBy(userIdentity);
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
        entity.setDueDate(dto.getDueDate());
        entity.setDescription(dto.getDescription());
        // User relationship (set the user in session)
        entity.setCreatedBy(authService.getCurrentUser());
        // bucket relationship
        Bucket bucket = new Bucket();
        bucket.setId(dto.getBucket().getId());
        entity.setBucket(bucket);
        // subtasks omitted for simplicity
        return entity;
    }
}
