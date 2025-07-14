package com.kanban.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanban.app.models.entities.Bucket;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
