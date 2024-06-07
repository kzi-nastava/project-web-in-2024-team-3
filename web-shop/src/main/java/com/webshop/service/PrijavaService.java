package com.webshop.service;

import com.webshop.dtos.PrijavaDto;
import com.webshop.model.*;
import com.webshop.repository.KorisnikRepository;
import com.webshop.repository.PrijavaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrijavaService {

    @Autowired
    private PrijavaRepository prijavaRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private RecenzijaService recenzijaService;

    public Prijava getPrijavaById(Long id) {
        Optional<Prijava> prijavaProfila = prijavaRepository.findById(id);



        return prijavaProfila.orElse(null);
    }

    public void savePrijava(Prijava prijavaProfila) {

        prijavaRepository.save(prijavaProfila);
    }

    public Prijava prijavi(Long prodavacId, PrijavaDto razlog, Long kupacId) {
        Kupac kupac = (Kupac) korisnikRepository.findById(kupacId).get();
        Prodavac prodavac = (Prodavac) korisnikRepository.findById(prodavacId).get();

        if (recenzijaService.razmena(kupacId, prodavacId)) {
            Prijava prijavaProfila = new Prijava(razlog);
            prijavaProfila.setOdnosiPrijava(prodavac);
            prijavaProfila.setPodneoPrijavu(kupac);
            return prijavaRepository.save(prijavaProfila);
        }
        return null;
    }

    public List<Prijava> getPrijavaList() {
        return prijavaRepository.findAll();
    }
}
