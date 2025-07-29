package com.kanban.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.BucketDTO;
import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.TaskDTO;
import com.kanban.app.models.entities.Board;
import com.kanban.app.models.entities.Bucket;
import com.kanban.app.models.entities.Task;
import com.kanban.app.repositories.BucketRepository;
import com.kanban.app.services.auth.AuthService;
import com.kanban.app.services.interfaces.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Autowired
    private BucketRepository bucketRepository;
    @Autowired
    private AuthService authService;

    @Override
    public BucketDTO findById(Long id) {
        return bucketRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<BucketDTO> findAll() {
        return bucketRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public BucketDTO save(BucketDTO dto) {
        Bucket entity;
        boolean isUpdate = dto.getId() != null && bucketRepository.existsById(dto.getId());
        if (isUpdate) {
            entity = bucketRepository.findById(dto.getId()).orElseThrow();
            entity.setName(dto.getName());
            entity.setColor(dto.getColor());
            entity.setDescription(dto.getDescription());
            entity.setCreatedAt(dto.getCreatedAt());
            // Board relationship
            Long boardId = dto.getBoard().getId();
            Board board = new Board();
            board.setId(boardId);
            entity.setBoard(board);
            // User relationship (set the user in session)
            entity.setCreatedBy(authService.getCurrentUser());
            // Tasks: mantener los existentes, eliminar los que no estén en el DTO, agregar/actualizar los que vienen
            if (dto.getTasks() != null) {
                entity.getTasks().removeIf(task -> dto.getTasks().stream().noneMatch(t -> t.getId() != null && t.getId().equals(task.getId())));
                for (TaskDTO taskDTO : dto.getTasks()) {
                    Task task = null;
                    if (taskDTO.getId() != null) {
                        task = entity.getTasks().stream().filter(t -> t.getId().equals(taskDTO.getId())).findFirst().orElse(null);
                    }
                    if (task == null) {
                        task = new Task();
                        entity.getTasks().add(task);
                    }
                    task.setId(taskDTO.getId());
                    task.setName(taskDTO.getName());
                    task.setDescription(taskDTO.getDescription());
                    task.setPriority(taskDTO.getPriority());
                    task.setStartDate(taskDTO.getStartDate());
                    task.setDueDate(taskDTO.getDueDate());
                    task.setCreatedAt(taskDTO.getCreatedAt());
                    task.setBucket(entity);
                }
            }
        } else {
            entity = toEntity(dto);
        }
        return toDTO(bucketRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        bucketRepository.deleteById(id);
    }

    @Override
    public Set<BucketDTO> getBucketsByBoardId(Long id){
        List<Bucket> bucketList = bucketRepository.findAllByBoardId(id);
        Set<BucketDTO> bucketSet = new HashSet<>();
        for(Bucket bucket : bucketList){
            BucketDTO dto = new BucketDTO();
            dto.setId(bucket.getId());
            dto.setName(bucket.getName());
            dto.setDescription(bucket.getDescription());
            dto.setColor(bucket.getColor());
            dto.setCreatedAt(bucket.getCreatedAt());
            // board relationship
            EntityIdentifier boardIdentity = new EntityIdentifier();
            boardIdentity.setId(bucket.getBoard().getId());
            dto.setBoard(boardIdentity);
            // user relationship
            EntityIdentifier userIdentity = new EntityIdentifier();
            userIdentity.setId(bucket.getCreatedBy().getId());
            dto.setCreatedBy(userIdentity);
            bucketSet.add(dto);
        }
        return bucketSet;
    }

    private BucketDTO toDTO(Bucket bucket) {
        BucketDTO dto = new BucketDTO();
        dto.setId(bucket.getId());
        dto.setName(bucket.getName());
        dto.setColor(bucket.getColor());
        dto.setDescription(bucket.getDescription());
        dto.setCreatedAt(bucket.getCreatedAt());
        // Board relationship
        EntityIdentifier boardIdentity = new EntityIdentifier();
        boardIdentity.setId(bucket.getBoard().getId());
        dto.setBoard(boardIdentity);
        // User identity relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        Long userId = bucket.getCreatedBy().getId();
        userIdentity.setId(userId);
        dto.setCreatedBy(userIdentity);
        // Tasks
        if (bucket.getTasks() != null) {
            dto.setTasks(bucket.getTasks().stream().map(task -> {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setId(task.getId());
                taskDTO.setName(task.getName());
                taskDTO.setDescription(task.getDescription());
                taskDTO.setPriority(task.getPriority());
                taskDTO.setStartDate(task.getStartDate());
                taskDTO.setDueDate(task.getDueDate());
                taskDTO.setCreatedAt(task.getCreatedAt());
                // bucket y createdBy pueden omitirse o mapearse según necesidad
                return taskDTO;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    private Bucket toEntity(BucketDTO dto) {
        Bucket entity = new Bucket();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setColor(dto.getColor());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        // Board relationship
        Long boardId = dto.getBoard().getId();
        Board board = new Board();
        board.setId(boardId);
        entity.setBoard(board);
        // User relationship (set the user in session)
        entity.setCreatedBy(authService.getCurrentUser());
        // Tasks
        if (dto.getTasks() != null) {
            entity.setTasks(dto.getTasks().stream().map(taskDTO -> {
                Task task = new Task();
                task.setId(taskDTO.getId());
                task.setName(taskDTO.getName());
                task.setDescription(taskDTO.getDescription());
                task.setPriority(taskDTO.getPriority());
                task.setStartDate(taskDTO.getStartDate());
                task.setDueDate(taskDTO.getDueDate());
                task.setCreatedAt(taskDTO.getCreatedAt());
                task.setBucket(entity);
                return task;
            }).collect(Collectors.toList()));
        }
        return entity;
    }
}
