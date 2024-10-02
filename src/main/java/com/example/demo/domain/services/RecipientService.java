package com.example.demo.domain.services;

import com.example.demo.domain.dtos.RecipientDTO;
import com.example.demo.domain.models.Recipient;
import com.example.demo.domain.repositories.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecipientService {

    @Autowired
    RecipientRepository recipientRepository;


    public Recipient saveRecipient(RecipientDTO recipientDTO) {
        if (recipientDTO.cpf().isEmpty()) {
            throw new IllegalArgumentException("Por favor, informe o CPF");
        }
        if (recipientDTO.name().isEmpty()) {
            throw new IllegalArgumentException("Por favor, informe um nome");
        }

        Recipient recipient = recipientRepository.findRecipientByCpf(recipientDTO.cpf());
        if (recipient != null) {
            throw new DuplicateKeyException("Já existe um destinatário cadastrado com este CPF");
        }

        Recipient recipientToSave = new Recipient();

        recipientToSave.setCpf(recipientDTO.cpf());
        recipientToSave.setName(recipientDTO.name());

        return recipientRepository.save(recipientToSave);
    }

    public List<Recipient> findAllRecipients(){
        List<Recipient> allRecipients = new ArrayList<>();
        allRecipients = recipientRepository.findAll();
        if (allRecipients.isEmpty()) {
            throw new NoSuchElementException("Não há nenhum destinatário cadastrado");
        }
        return allRecipients;
    }

    public Recipient findRecipientByCpf(String cpf) {
        if (cpf.isEmpty()) {
            throw new IllegalArgumentException("Por favor, informe o CPF.");
        }
        Recipient searchedRecipient = recipientRepository.findRecipientByCpf(cpf);
        if (searchedRecipient == null) {
            throw new NoSuchElementException("Não há destinatário cadastrado com este CPF");
        }
        return searchedRecipient;
    }
}
