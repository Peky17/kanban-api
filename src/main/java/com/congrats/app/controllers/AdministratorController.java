package com.congrats.app.controllers;

import com.congrats.app.models.entities.AdministratorEntity;
import com.congrats.app.services.domain.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/administrators")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping
    public List<AdministratorEntity> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministratorEntity> getAdministratorById(
            @PathVariable Long id
    ) {
        Optional<AdministratorEntity> administrator = administratorService.getAdministratorById(id);
        return administrator.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdministratorEntity> createAdministrator(
            @RequestBody AdministratorEntity administrator
    ) {
        AdministratorEntity savedAdministrator = administratorService.saveAdministrator(administrator);
        return ResponseEntity.ok(savedAdministrator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministratorEntity> updateAdministrator(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long id,
            @RequestBody AdministratorEntity administratorDetails
    ) {
        AdministratorEntity updatedAdministrator = administratorService.updateAdministrator(id, administratorDetails);
        return ResponseEntity.ok(updatedAdministrator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrator(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long id
    ) {
        administratorService.deleteAdministrator(id);
        return ResponseEntity.noContent().build();
    }
}