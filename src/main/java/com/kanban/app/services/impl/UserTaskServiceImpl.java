package com.kanban.app.services.impl;

import com.kanban.app.models.dto.EntityIdentifier;
import com.kanban.app.models.dto.TaskDTO;
import com.kanban.app.models.dto.UserDTO;
import com.kanban.app.models.dto.UserTaskDTO;
import com.kanban.app.models.entities.Task;
import com.kanban.app.models.entities.User;
import com.kanban.app.models.entities.UserTask;
import com.kanban.app.repositories.UserTaskRepository;
import com.kanban.app.services.auth.AuthService;
import com.kanban.app.services.interfaces.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserTaskServiceImpl implements UserTaskService {
    @Autowired
    private UserTaskRepository userTaskRepository;
    @Autowired
    private AuthService authService;

    @Override
    public UserTaskDTO findById(Long id) {
        return userTaskRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<UserTaskDTO> findAll() {
        return userTaskRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserTaskDTO save(UserTaskDTO dto) {
        UserTask entity = toEntity(dto);
        return toDTO(userTaskRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        userTaskRepository.deleteById(id);
    }

    @Override
    public Set<UserDTO> getUsersByTaskId(Long id){
        List<UserTask> userTasks = userTaskRepository.getAllByTaskId(id);
        Set<UserDTO> userSet = new HashSet<>();
        for(UserTask userTask : userTasks){
            UserDTO dto = new UserDTO();
            User entity = userTask.getUser();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setEmail(entity.getEmail());
            dto.setCellphone(entity.getCellphone());
            dto.setPassword(entity.getPassword());
            EntityIdentifier roleIdentity = new EntityIdentifier();
            roleIdentity.setId(entity.getRole().getId());
            dto.setRole(roleIdentity);
            userSet.add(dto);
        }
        return userSet;
    }

    @Override
    public Set<TaskDTO> getTasksByUserId(Long id){
        List<UserTask> tasksUser = userTaskRepository.getAllByUserId(id);
        Set<TaskDTO> tasksSet = new HashSet<>();
        for(UserTask taskUser : tasksUser){
            TaskDTO dto = new TaskDTO();
            Task entity = taskUser.getTask();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setPriority(entity.getPriority());
            dto.setStartDate(entity.getStartDate());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setDueDate(entity.getDueDate());
            EntityIdentifier bucketIdentity = new EntityIdentifier();
            bucketIdentity.setId(entity.getBucket().getId());
            dto.setBucket(bucketIdentity);
            EntityIdentifier authorIdentity = new EntityIdentifier();
            authorIdentity.setId(entity.getCreatedBy().getId());
            dto.setCreatedBy(authorIdentity);
            tasksSet.add(dto);
        }

        return tasksSet;
    }

    private UserTaskDTO toDTO(UserTask userTask) {
        UserTaskDTO dto = new UserTaskDTO();
        dto.setId(userTask.getId());
        dto.setCompleted(userTask.isCompleted());
        // User identity relationship
        EntityIdentifier userIdentity = new EntityIdentifier();
        userIdentity.setId(userTask.getUser().getId());
        dto.setUser(userIdentity);
        // Task identity
        EntityIdentifier taskIdentity = new EntityIdentifier();
        Long taskId = userTask.getTask().getId();
        taskIdentity.setId(taskId);
        dto.setTask(taskIdentity);
        // Subtasks omitted for simplicity
        return dto;
    }

    private UserTask toEntity(UserTaskDTO dto) {
        UserTask entity = new UserTask();
        entity.setId(dto.getId());
        entity.setCompleted(dto.isCompleted());
        // User relationship
        User user = new User();
        Long userId = dto.getUser().getId();
        user.setId(userId);
        entity.setUser(user);
        // Task relationship
        Task task = new Task();
        Long taskId = dto.getTask().getId();
        task.setId(taskId);
        entity.setTask(task);
        // subtasks omitted for simplicity
        return entity;
    }
}
