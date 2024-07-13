package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.TaskRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseRegisterDTO;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.mappers.entities.TaskMapper;
import com.donatoordep.rg.code.repositories.TaskRepository;
import com.donatoordep.rg.code.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseRegisterDTO create(User user, TaskRequestRegisterDTO request) {
        user.addTask(TaskMapper.toEntity(request));
        user = userRepository.save(user);
        return TaskMapper.toResponse(user.getTasks().get(user.getTasks().size() - 1), user);
    }
}