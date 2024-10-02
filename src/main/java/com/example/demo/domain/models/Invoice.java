package com.example.demo.domain.models;

import com.example.demo.domain.models.Packages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

@Entity
@Table(name = "invoice_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "packages_id", nullable = false)
    @JsonIgnore
    private Packages packages;

    public synchronized String generateInvoiceNumber() {
        String nfNumber = RandomStringUtils.randomNumeric(8);
        return String.format("NF-%s", nfNumber);
    }
}