package com.donatoordep.rg.code.controllers;

import com.donatoordep.rg.code.dtos.request.TaskRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseGetAllDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseRegisterDTO;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskResponseRegisterDTO> create(@AuthenticationPrincipal User user, @RequestBody @Valid TaskRequestRegisterDTO request) {
        TaskResponseRegisterDTO response = service.create(user, request);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getIdentifier()).toUri()).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        service.deleteById(user, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseGetAllDTO>> getTasksByUser(@AuthenticationPrincipal User user, Pageable pageable){
        return ResponseEntity.ok().body(service.getTasksByUser(user, pageable));
    }
}