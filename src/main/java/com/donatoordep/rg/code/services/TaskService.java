package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.TaskRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseGetAllDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseRegisterDTO;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.mappers.entities.TaskMapper;
import com.donatoordep.rg.code.repositories.TaskRepository;
import com.donatoordep.rg.code.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public TaskResponseRegisterDTO create(User user, TaskRequestRegisterDTO request) {
        Task taskEntity = TaskMapper.toEntity(request);
        user.addTask(taskEntity);
        userRepository.save(user);
        taskRepository.save(taskEntity);
        return TaskMapper.toResponse(user.getTasks().get(user.getTasks().size() - 1), user);
    }

    public void deleteById(User user, UUID id) {
        user.deleteTaskById(user.findTaskById(id).getId());
        userRepository.save(user);
    }

}