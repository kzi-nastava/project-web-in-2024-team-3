package com.webshop.service;

import com.webshop.model.Kategorija;
import com.webshop.repository.KategorijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategorijaService {

    @Autowired
    KategorijaRepository kategorijaRepository;

    public Kategorija getKategorija(String naziv) {
        Kategorija kategorija = kategorijaRepository.findByNaziv(naziv);
        if (kategorija == null) {
            return null;
        }

        return kategorija;
    }

    public List<Kategorija> getKategorijaList() {
        return kategorijaRepository.findAll();
    }

}
