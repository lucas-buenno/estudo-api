package com.example.demo.domain.services;

import com.example.demo.domain.dtos.PackagesDTO;
import com.example.demo.domain.enums.StatusPackage;
import com.example.demo.domain.models.Invoice;
import com.example.demo.domain.models.Packages;
import com.example.demo.domain.models.Recipient;
import com.example.demo.domain.repositories.InvoiceRepository;
import com.example.demo.domain.repositories.PackagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PackagesService {

    @Autowired
    PackagesRepository packagesRepository;

    @Autowired
    RecipientService recipientService;

    @Autowired
    InvoiceRepository invoiceRepository;


    public Packages newPackage(PackagesDTO packagesDTO) {
        if (packagesDTO.cpf().isEmpty()) {
            throw new IllegalArgumentException("Por favor, informe um CPF.");
        }

        if (packagesDTO.destinationAddress().isEmpty()) {
            throw new IllegalArgumentException("Por favor, insira um endereço.");
        }

        Recipient recipient = recipientService.findRecipientByCpf(packagesDTO.cpf().toUpperCase());

        if (recipient == null) {
            throw new IllegalArgumentException(
                    "Não existe um destinatário cadastrado com este CPF. Por favor, cadastre-o primeiro"
            );
        }

        Packages newPackage = new Packages();
        newPackage.setPostDate(LocalDateTime.now());
        newPackage.setEstimatedDeliveryDate(LocalDate.now().plusDays(7));
        newPackage.setStatusPackage(StatusPackage.WAITING_POSTAGE);
        newPackage.setDestinationAddress(packagesDTO.destinationAddress());
        newPackage.setRecipient(recipient);
        packagesRepository.save(newPackage);

        Invoice invoice = new Invoice();
        String invoiceNumber = invoice.generateInvoiceNumber();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setPackages(newPackage);
        invoiceRepository.save(invoice);

        return packagesRepository.save(newPackage);
    }

    public List<Packages> findAllPackages() {
        List<Packages> allPackages = packagesRepository.findAll();

        if (allPackages.isEmpty()) {
            throw new NoSuchElementException("Ainda não há encomendas cadastradas.");
        }

        return allPackages;
    }
}
