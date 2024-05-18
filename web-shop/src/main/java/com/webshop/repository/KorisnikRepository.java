package com.webshop.repository;

import com.webshop.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KorisnikRepository extends JpaRepository<Korisnik,Long> {

    Korisnik findByImeAndPrezime(String ime,String prezime);

    Korisnik findByEmail(String email);

    Korisnik findByKorisnickoIme(String korisnickoIme);

    Korisnik findByKorisnickoImeAndEmail(String korisnickoIme,String email);

}
