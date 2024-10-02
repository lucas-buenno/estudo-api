package com.example.demo.domain.dtos;

import com.example.demo.domain.enums.StatusPackage;
import com.example.demo.domain.models.Invoice;
import com.example.demo.domain.models.Packages;
import com.example.demo.domain.models.Recipient;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


public record PackagesDTO(String cpf, String destinationAddress, RecipientDTO recipient) {



}
