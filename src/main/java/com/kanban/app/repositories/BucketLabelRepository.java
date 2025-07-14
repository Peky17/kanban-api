package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.BucketLabel;

public interface BucketLabelRepository extends JpaRepository<BucketLabel, Long> {
}
