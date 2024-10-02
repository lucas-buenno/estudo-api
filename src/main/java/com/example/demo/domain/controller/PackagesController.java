package com.example.demo.domain.controller;

import com.example.demo.domain.dtos.PackagesDTO;
import com.example.demo.domain.models.Packages;
import com.example.demo.domain.repositories.PackagesRepository;
import com.example.demo.domain.services.PackagesService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tuple.CreationTimestampGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/packages")
@Slf4j
public class PackagesController {

    @Autowired
    PackagesService packagesService;


    @PostMapping("/new")
    public ResponseEntity<?> newPackage(@RequestBody PackagesDTO packagesDTO) {
        try {
            Packages newPackage = packagesService.newPackage(packagesDTO);
            log.info("Encomenda salva");
            return ResponseEntity.status(HttpStatus.CREATED).body("A encomenda foi salva");

        } catch (IllegalArgumentException | NoSuchElementException e) {
            log.error("Não conseguiu criar o pacote para o destinarário:" + packagesDTO.cpf());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllPackages() {
        try {
            List<Packages> allPackages = packagesService.findAllPackages();
            log.info("Encontrou pacotes cadastrados");
            return ResponseEntity.ok(allPackages);
        } catch (NoSuchElementException e) {
            log.info("Não encontrou nenhum pacote salvo na lista");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
