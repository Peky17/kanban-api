package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.BucketLabel;

import java.util.List;

public interface BucketLabelRepository extends JpaRepository<BucketLabel, Long> {
    List<BucketLabel> findAllByBucketId(Long id);
}
