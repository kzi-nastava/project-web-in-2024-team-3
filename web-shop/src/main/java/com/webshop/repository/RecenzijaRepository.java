package com.webshop.repository;

import com.webshop.model.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecenzijaRepository extends JpaRepository<Recenzija,Long> {

    void deleteById(Long id);

    List<Recenzija> findAllByKorisnik_Id(Long id);

}
