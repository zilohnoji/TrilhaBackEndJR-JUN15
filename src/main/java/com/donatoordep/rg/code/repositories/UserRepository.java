package com.donatoordep.rg.code.repositories;

import com.donatoordep.rg.code.entities.User;
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
}