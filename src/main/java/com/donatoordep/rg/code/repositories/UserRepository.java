package com.donatoordep.rg.code.repositories;

import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE UPPER(u.email) LIKE UPPER(CONCAT('%', :username, '%'))")
    UserDetails findByEmailForLoadUserDetails(String username);

    @Query("SELECT u FROM User u WHERE UPPER(u.email) LIKE UPPER(CONCAT('%', :email, '%'))")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE UPPER(u.code.code) LIKE UPPER(CONCAT('%', :code, '%'))")
    Optional<User> findByEmailCodeConfirmation(String code);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(String email);

    @Query("SELECT t FROM User u FETCH JOIN u.tasks t WHERE UPPER(u.email) LIKE UPPER(CONCAT('%', :username,'%'))")
    Page<Task> getTasks(String username);
}