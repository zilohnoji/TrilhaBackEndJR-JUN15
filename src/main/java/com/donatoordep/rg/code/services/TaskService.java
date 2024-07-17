package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.TaskRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseGetAllDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseRegisterDTO;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.exceptions.ONBEntityNotFoundException;
import com.donatoordep.rg.code.mappers.entities.TaskMapper;
import com.donatoordep.rg.code.repositories.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private final TaskSpecification taskSpecification;

    @Autowired
    public TaskService(TaskSpecification taskSpecification) {
        this.taskSpecification = taskSpecification;
    }

    public TaskResponseRegisterDTO create(User user, TaskRequestRegisterDTO request) {
        Task taskEntity = TaskMapper.toEntity(request);
        taskEntity.setUser(user);
        taskEntity = taskSpecification.save(taskEntity);
        return TaskMapper.toResponse(taskEntity, user);
    }

    public void deleteById(User user, UUID id) {
        List<Task> taskList = taskSpecification.getTasksByUserId(user.getId());
        Task task = taskList.stream().filter(item -> item.getId().equals(id)).findFirst().orElseThrow(ONBEntityNotFoundException::new);
        taskSpecification.delete(task);
    }

    public Page<TaskResponseGetAllDTO> getTasksByUser(User user, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title"));
        return taskSpecification.getTasksByUserId(user.getId(), pageRequest).map(TaskResponseGetAllDTO::new);
    }
}