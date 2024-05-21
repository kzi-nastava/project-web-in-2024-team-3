package com.webshop.service;

import com.webshop.model.Kategorija;
import com.webshop.model.Korisnik;
import com.webshop.model.Proizvod;
import com.webshop.model.TIP;
import com.webshop.repository.ProizvodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProizvodService {

    @Autowired
    private ProizvodRepository proizvodRepository;

    public Page<Proizvod> getProizvodLista(int page, int size) {
        Pageable desetProizvoda = PageRequest.of(page, size);
        return proizvodRepository.findAll(desetProizvoda);
    }

    public List<Proizvod> findAll() { return proizvodRepository.findAll();}

    public List<Proizvod> pronadjiPoNazivuIOpisu(String naziv, String opis) {
        List<Proizvod> proizvodi = proizvodRepository.findAllByNazivContainsIgnoreCaseOrOpisContainsIgnoreCase(naziv, opis);
        if(proizvodi.isEmpty())
            return null;
        else
            return proizvodi;
    }



    public List<Proizvod> pronadjiPoKategoriji(Long kategorijaId) {
        List<Proizvod> proizvodi = proizvodRepository.findByKategorijaId(kategorijaId);

        if(proizvodi.isEmpty()) {
            return null;
        }

        return proizvodi;
    }


    public Proizvod pronadjiPoId(Long id) {
        Optional<Proizvod> proizvod = proizvodRepository.findById(id);

        if(proizvod.isEmpty()) {
            return null;
        }

        return proizvod.get();
    }


    public List<Proizvod> filtrirajProizvod(Double min, Double max, TIP tip, String kateogrija) {
        Optional<List<Proizvod>> proizvodi = proizvodRepository.findAllByFilter(min, max, tip, kateogrija);

        return proizvodi.orElse(null);
    }

    public void obrisiProizvodPoIdProdavca(Long id) {
        proizvodRepository.deleteByProdavacId(id);
    }

    public void saveProizvod(Proizvod proizvod) {
        proizvodRepository.save(proizvod);
    }
}
