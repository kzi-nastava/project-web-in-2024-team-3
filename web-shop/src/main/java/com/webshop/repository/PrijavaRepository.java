package com.webshop.repository;

import com.webshop.model.Prijava;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrijavaRepository extends JpaRepository<Prijava, Long> {

    Optional<Prijava> findById(Long id);
}
