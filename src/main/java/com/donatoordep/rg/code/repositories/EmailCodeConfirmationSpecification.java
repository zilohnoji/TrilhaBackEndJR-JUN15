package com.donatoordep.rg.code.repositories;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface EmailCodeConfirmationSpecification extends JpaRepository<EmailCodeConfirmation, UUID> {

    @Query("SELECT u FROM EmailCodeConfirmation u WHERE u.code = :token")
    Optional<EmailCodeConfirmation> findByToken(String token);
}