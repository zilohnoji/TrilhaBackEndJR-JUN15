package com.donatoordep.rg.code.controllers;

import com.donatoordep.rg.code.dtos.request.TaskRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.request.TaskRequestUpdateDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseGetAllDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseRegisterDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseUpdateDTO;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(description = "Cadastrar uma nova tarefa", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarefa cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no cadastramento, tarefa não cadastrada")
    })
    public ResponseEntity<TaskResponseRegisterDTO> create(@AuthenticationPrincipal User user, @RequestBody @Valid TaskRequestRegisterDTO request) {
        TaskResponseRegisterDTO response = service.create(user, request);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getIdentifier()).toUri()).body(response);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Deletar uma tarefa", method = "DELETE")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na deleção da tarefa, tarefa não deletada")
    })
    public ResponseEntity<Void> deleteById(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        service.deleteById(user, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @Operation(description = "Pegar tarefas do usuário autenticado", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas recuperadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na recuperação das tarefas, tarefas não recuperadas")
    })
    public ResponseEntity<Page<TaskResponseGetAllDTO>> getTasksByUser(@AuthenticationPrincipal User user, Pageable pageable) {
        return ResponseEntity.ok().body(service.getTasksByUser(user, pageable));
    }

    @PostMapping(path = "/update")
    @Operation(description = "Atualizar uma tarefa", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização da tarefa, tarefa não atualizada")
    })
    public ResponseEntity<TaskResponseUpdateDTO> update(@AuthenticationPrincipal User user, @RequestBody @Valid TaskRequestUpdateDTO request) {
        return ResponseEntity.ok().body(service.update(user, request));
    }
}