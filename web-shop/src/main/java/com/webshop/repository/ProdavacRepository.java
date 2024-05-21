package com.webshop.repository;

import com.webshop.model.Prodavac;
import com.webshop.model.Uloga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdavacRepository extends JpaRepository<Prodavac,Long> {

    List<Prodavac> findAllByUloga(Uloga uloga);

}
