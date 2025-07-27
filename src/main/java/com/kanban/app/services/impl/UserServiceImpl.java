package com.kanban.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.UserDTO;
import com.kanban.app.models.entities.User;
import com.kanban.app.repositories.UserRepository;
import com.kanban.app.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO findById(Long id) {
        return userRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO save(UserDTO dto) {
        User entity = toEntity(dto);
        return toDTO(userRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setCellphone(user.getCellphone());
        if (user.getRole() != null) dto.setRole(new EntityIdentifier(user.getRole().getId()));
        dto.setWon(user.getWon());
        dto.setEmployeeNumber(user.getEmployeeNumber());
        return dto;
    }

    private User toEntity(UserDTO dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setCellphone(dto.getCellphone());
        entity.setWon(dto.getWon());
        entity.setEmployeeNumber(dto.getEmployeeNumber());
        // role omitted for simplicity
        return entity;
    }
}
