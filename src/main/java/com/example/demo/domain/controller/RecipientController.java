package com.example.demo.domain.controller;

import com.example.demo.domain.dtos.RecipientDTO;
import com.example.demo.domain.models.Recipient;
import com.example.demo.domain.services.RecipientService;
import jakarta.persistence.GeneratedValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/recipients")
@Slf4j
public class RecipientController {

    @Autowired
    RecipientService recipientService;


    @PostMapping
    public ResponseEntity<?> saveRecipient(@RequestBody RecipientDTO recipientDTO) {
        try {
            recipientService.saveRecipient(recipientDTO);
            log.info("O destinatário foi salvo: ID " + recipientDTO.id());
            return ResponseEntity.status(HttpStatus.CREATED).body("O destinatário foi salvo.");
        } catch (IllegalArgumentException | DuplicateKeyException e) {
            log.error("Não conseguiu salvar o destinarário" + recipientDTO.id());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllRecipients() {
        try{
            List<Recipient> allRecipients = new ArrayList<>();
            allRecipients = recipientService.findAllRecipients();
            log.info("Destinatários encontrados");
            return ResponseEntity.ok(allRecipients);
        } catch (NoSuchElementException e) {
            log.info("Nenhum destinarário encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findRecipientByCpf(@Param("cpf") @PathVariable String cpf) {
        try {
            Recipient searchedRecipient = recipientService.findRecipientByCpf(cpf);
            log.info("Destinatário encontrado");
            return ResponseEntity.ok(searchedRecipient);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            log.warn("Tentou buscar por um CPF inválido ou inexistente na DB " + cpf);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
