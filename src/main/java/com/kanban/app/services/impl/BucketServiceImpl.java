package com.kanban.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kanban.app.models.entities.Board;
import com.kanban.app.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.BucketDTO;
import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.entities.Bucket;
import com.kanban.app.repositories.BucketRepository;
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
        Bucket entity = toEntity(dto);
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
        // Tasks omitted for simplicity
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
        // tasks omitted for simplicity
        return entity;
    }
}
