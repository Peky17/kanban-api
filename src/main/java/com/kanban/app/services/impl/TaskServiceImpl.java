package com.kanban.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.SubtaskDTO;
import com.kanban.app.models.dto.TaskDTO;
import com.kanban.app.models.entities.Bucket;
import com.kanban.app.models.entities.Subtask;
import com.kanban.app.models.entities.Task;
import com.kanban.app.repositories.TaskRepository;
import com.kanban.app.services.auth.AuthService;
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
        Task entity;
        boolean isUpdate = dto.getId() != null && taskRepository.existsById(dto.getId());
        if (isUpdate) {
            entity = taskRepository.findById(dto.getId()).orElseThrow();
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
            // Subtasks: mantener los existentes, eliminar los que no estén en el DTO, agregar/actualizar los que vienen
            if (dto.getSubtasks() != null) {
                entity.getSubtasks().removeIf(subtask -> dto.getSubtasks().stream().noneMatch(s -> s.getId() != null && s.getId().equals(subtask.getId())));
                for (SubtaskDTO subtaskDTO : dto.getSubtasks()) {
                    Subtask subtask = null;
                    if (subtaskDTO.getId() != null) {
                        subtask = entity.getSubtasks().stream().filter(s -> s.getId().equals(subtaskDTO.getId())).findFirst().orElse(null);
                    }
                    if (subtask == null) {
                        subtask = new Subtask();
                        entity.getSubtasks().add(subtask);
                    }
                    subtask.setId(subtaskDTO.getId());
                    subtask.setName(subtaskDTO.getName());
                    subtask.setCreatedAt(subtaskDTO.getCreatedAt());
                    subtask.setPriority(subtaskDTO.getPriority());
                    subtask.setStartdDate(subtaskDTO.getStartdDate());
                    subtask.setDueDate(subtaskDTO.getDueDate());
                    subtask.setTask(entity);
                }
            }
        } else {
            entity = toEntity(dto);
        }
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
        // Subtasks
        if (task.getSubtasks() != null) {
            java.util.List<SubtaskDTO> subtaskDTOs = task.getSubtasks().stream()
                .map(subtask -> {
                    SubtaskDTO subtaskDTO = new SubtaskDTO();
                    subtaskDTO.setId(subtask.getId());
                    subtaskDTO.setName(subtask.getName());
                    subtaskDTO.setCreatedAt(subtask.getCreatedAt());
                    subtaskDTO.setPriority(subtask.getPriority());
                    subtaskDTO.setStartdDate(subtask.getStartdDate());
                    subtaskDTO.setDueDate(subtask.getDueDate());
                    // createdBy y task pueden omitirse o mapearse según necesidad
                    return subtaskDTO;
                })
                .collect(java.util.stream.Collectors.toList());
            dto.setSubtasks(subtaskDTOs);
        }
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
        // Subtasks
        if (dto.getSubtasks() != null) {
            if (entity.getSubtasks() == null) {
                entity.setSubtasks(dto.getSubtasks().stream().map(subtaskDTO -> {
                    Subtask subtask = new Subtask();
                    subtask.setId(subtaskDTO.getId());
                    subtask.setName(subtaskDTO.getName());
                    subtask.setCreatedAt(subtaskDTO.getCreatedAt());
                    subtask.setPriority(subtaskDTO.getPriority());
                    subtask.setStartdDate(subtaskDTO.getStartdDate());
                    subtask.setDueDate(subtaskDTO.getDueDate());
                    subtask.setTask(entity);
                    // createdBy puede omitirse o mapearse según necesidad
                    return subtask;
                }).collect(java.util.stream.Collectors.toList()));
            } else {
                entity.getSubtasks().clear();
                entity.getSubtasks().addAll(dto.getSubtasks().stream().map(subtaskDTO -> {
                    Subtask subtask = new Subtask();
                    subtask.setId(subtaskDTO.getId());
                    subtask.setName(subtaskDTO.getName());
                    subtask.setCreatedAt(subtaskDTO.getCreatedAt());
                    subtask.setPriority(subtaskDTO.getPriority());
                    subtask.setStartdDate(subtaskDTO.getStartdDate());
                    subtask.setDueDate(subtaskDTO.getDueDate());
                    subtask.setTask(entity);
                    return subtask;
                }).collect(java.util.stream.Collectors.toList()));
            }
        }
        return entity;
    }
}
