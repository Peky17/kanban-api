package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Bucket;

import java.util.List;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    List<Bucket> findAllByBoardId(Long id);
}
