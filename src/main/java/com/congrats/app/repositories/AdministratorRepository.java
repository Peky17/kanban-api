package com.congrats.app.repositories;

import com.congrats.app.models.entities.AdministratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Long> {
    Optional<AdministratorEntity> findByEmail(String email);
}