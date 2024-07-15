package com.donatoordep.rg.code.repositories;

import com.donatoordep.rg.code.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT t FROM Task t WHERE t.user.id = :id")
    Page<Task> getTasksByUserId(UUID id, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.user.id = :id")
    List<Task> getTasksByUserId(UUID id);
}