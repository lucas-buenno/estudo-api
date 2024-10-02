package com.example.demo.domain.repositories;

import com.example.demo.domain.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RecipientRepository extends JpaRepository<Recipient, UUID> {

    Recipient findRecipientByCpf(@Param("cpf") String cpf);
}
