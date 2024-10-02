package com.example.demo.domain.models;

import com.example.demo.domain.enums.StatusPackage;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_packages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Packages implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy - HH:mm:ss")
    private LocalDateTime postDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate estimatedDeliveryDate;

    @Enumerated(EnumType.STRING)
    private StatusPackage statusPackage;
    private String destinationAddress;

    @OneToOne(mappedBy = "packages", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Invoice InvoiceNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id")
    @JsonBackReference
    private Recipient recipient;


}
