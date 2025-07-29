package com.kanban.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.BoardDTO;
import com.kanban.app.models.dto.BucketDTO;
import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.entities.Board;
import com.kanban.app.models.entities.Bucket;
import com.kanban.app.models.entities.Project;
import com.kanban.app.repositories.BoardRepository;
import com.kanban.app.services.auth.AuthService;
import com.kanban.app.services.interfaces.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private AuthService authService;

    @Override
    public BoardDTO findById(Long id) {
        return boardRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public BoardDTO save(BoardDTO dto) {
        Board entity;
        boolean isUpdate = dto.getId() != null && boardRepository.existsById(dto.getId());
        if (isUpdate) {
            entity = boardRepository.findById(dto.getId()).orElseThrow();
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setCreatedAt(dto.getCreatedAt());
            // Project relationship
            Long projectId = dto.getProject().getId();
            Project project = new Project();
            project.setId(projectId);
            entity.setProject(project);
            // User relationship (set the user in session)
            entity.setCreatedBy(authService.getCurrentUser());
            // Buckets: mantener los existentes, eliminar los que no estén en el DTO, agregar/actualizar los que vienen
            if (dto.getBuckets() != null) {
                entity.getBuckets().removeIf(bucket -> dto.getBuckets().stream().noneMatch(b -> b.getId() != null && b.getId().equals(bucket.getId())));
                for (BucketDTO bucketDTO : dto.getBuckets()) {
                    Bucket bucket = null;
                    if (bucketDTO.getId() != null) {
                        bucket = entity.getBuckets().stream().filter(b -> b.getId().equals(bucketDTO.getId())).findFirst().orElse(null);
                    }
                    if (bucket == null) {
                        bucket = new Bucket();
                        entity.getBuckets().add(bucket);
                    }
                    bucket.setId(bucketDTO.getId());
                    bucket.setName(bucketDTO.getName());
                    bucket.setDescription(bucketDTO.getDescription());
                    bucket.setCreatedAt(bucketDTO.getCreatedAt());
                    bucket.setBoard(entity);
                }
            }
        } else {
            entity = toEntity(dto);
        }
        return toDTO(boardRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Set<BoardDTO> getBoardsByProjectId(Long id){
        List<Board> boardList = boardRepository.findAllByProjectId(id);
        Set<BoardDTO> boardSet = new HashSet<>();
        for(Board board : boardList){
            BoardDTO dto = new BoardDTO();
            dto.setId(board.getId());
            dto.setName(board.getName());
            dto.setDescription(board.getDescription());
            dto.setCreatedAt(board.getCreatedAt());
            // Project associated
            EntityIdentifier projectIdentity = new EntityIdentifier();
            projectIdentity.setId(board.getProject().getId());
            dto.setProject(projectIdentity);
            // User associated
            EntityIdentifier userIdentity = new EntityIdentifier();
            userIdentity.setId(board.getCreatedBy().getId());
            dto.setCreatedBy(userIdentity);
            boardSet.add(dto);
        }
        return boardSet;
    }

    private BoardDTO toDTO(Board board) {
        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setName(board.getName());
        dto.setDescription(board.getDescription());
        dto.setCreatedAt(board.getCreatedAt());
        // Project relationship
        EntityIdentifier projectIdentity = new EntityIdentifier();
        projectIdentity.setId(board.getProject().getId());
        dto.setProject(projectIdentity);
        // User identity relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        Long userId = board.getCreatedBy().getId();
        userIdentity.setId(userId);
        dto.setCreatedBy(userIdentity);
        // Buckets
        if (board.getBuckets() != null) {
            dto.setBuckets(board.getBuckets().stream().map(bucket -> {
                BucketDTO bucketDTO = new BucketDTO();
                bucketDTO.setId(bucket.getId());
                bucketDTO.setName(bucket.getName());
                bucketDTO.setDescription(bucket.getDescription());
                bucketDTO.setCreatedAt(bucket.getCreatedAt());
                // Board y createdBy pueden omitirse o mapearse según necesidad
                return bucketDTO;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    private Board toEntity(BoardDTO dto) {
        Board entity = new Board();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        // Project relationship
        Long projectId = dto.getProject().getId();
        Project project = new Project();
        project.setId(projectId);
        entity.setProject(project);
        // User relationship (set the user in session)
        entity.setCreatedBy(authService.getCurrentUser());
        // Buckets
        if (dto.getBuckets() != null) {
            entity.setBuckets(dto.getBuckets().stream().map(bucketDTO -> {
                Bucket bucket = new Bucket();
                bucket.setId(bucketDTO.getId());
                bucket.setName(bucketDTO.getName());
                bucket.setDescription(bucketDTO.getDescription());
                bucket.setCreatedAt(bucketDTO.getCreatedAt());
                bucket.setBoard(entity);
                return bucket;
            }).collect(Collectors.toList()));
        }
        return entity;
    }
}
