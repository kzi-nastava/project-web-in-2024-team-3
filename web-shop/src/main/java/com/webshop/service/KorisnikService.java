package com.webshop.service;

import com.webshop.model.Korisnik;
import com.webshop.model.Prodavac;
import com.webshop.model.Uloga;
import com.webshop.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KorisnikService {

    @Autowired
    KorisnikRepository korisnikRepository;

    public Korisnik pronadjiMejl(String email) {
        Korisnik korisnik = korisnikRepository.findByEmail(email);

        if(korisnik == null) {
            return null;
        }

        return korisnik;
    }

    public Korisnik pronadjiKorisnickoIme(String korisnickoIme) {
        Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme);

        if(korisnik == null) {
            return null;
        }

        return korisnik;
    }

    public Korisnik pronadjiPoKorisnickomImenuIEmailu(String korisnickoIme, String email) {
        Korisnik korisnik = korisnikRepository.findByKorisnickoImeAndEmail(korisnickoIme,email);

        if(korisnik == null) {
            return null;
        }

        return korisnik;
    }

    public Korisnik login(String korisnickoIme,String lozinka) {
        Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka)) {
            return null;
        }
        return korisnik;
    }

    public Korisnik pronadjiPoId(Long id) {
        Optional<Korisnik> korisnik = korisnikRepository.findById(id);

        if(korisnik.isEmpty()) {
            return null;
        }

        return korisnik.get();
    }

    public void saveKorisnik(Korisnik korisnik) {
        korisnikRepository.save(korisnik);
    }

    public Prodavac pronadjiKupcaPoId(Long id) {
        Optional<Korisnik> optional = korisnikRepository.findById(id);
        if (optional.isPresent() && optional.get().getUloga() == Uloga.PRODAVAC) {
            return (Prodavac) optional.get();
        }
        return null;
    }

}
