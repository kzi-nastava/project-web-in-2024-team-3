package com.webshop.repository;

import com.webshop.model.Kategorija;
import com.webshop.model.Proizvod;
import com.webshop.model.TIP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Long> {

    List<Proizvod> findByNazivContainingOrOpisContainingIgnoreCase(String naziv, String opis);

    List<Proizvod> findByCenaBetween(double min_cena,double max_cena);

    List<Proizvod> findByTipProdaje(TIP tipProdaje);

    List<Proizvod> findByKategorija(Kategorija kategorija);

    Optional<Proizvod> findById(long id);
}
