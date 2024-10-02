package com.example.demo.domain.repositories;

import com.example.demo.domain.models.Packages;
import com.example.demo.domain.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PackagesRepository extends JpaRepository<Packages, UUID> {



}
