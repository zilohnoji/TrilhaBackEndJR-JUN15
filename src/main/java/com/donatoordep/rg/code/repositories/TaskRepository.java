package com.donatoordep.rg.code.repositories;

import com.donatoordep.rg.code.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}