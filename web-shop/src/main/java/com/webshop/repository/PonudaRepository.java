package com.webshop.repository;

import com.webshop.model.Kupac;
import com.webshop.model.Ponuda;
import com.webshop.model.Proizvod;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PonudaRepository extends JpaRepository<Ponuda, Long> {

    Ponuda findByKupac(Kupac kupac);

    Ponuda findTopByProizvodOrderByCenaDesc(Proizvod proizvod);

}
