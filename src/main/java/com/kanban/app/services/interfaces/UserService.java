package com.kanban.app.services.interfaces;

import java.util.List;

import com.kanban.app.models.dto.UserDTO;

public interface UserService {
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    UserDTO save(UserDTO dto);
    void delete(Long id);
}
