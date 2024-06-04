package com.webshop.repository;

import com.webshop.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik,Long> {

    Korisnik findByImeAndPrezime(String ime,String prezime);

    Optional<Korisnik> findById(Long id);

    Korisnik findByEmail(String email);

    Korisnik findByKorisnickoIme(String korisnickoIme);

    Korisnik findByKorisnickoImeAndEmail(String korisnickoIme,String email);

    Boolean existsKorisnikByEmail(String email);

    Boolean existsKorisnikByLozinka(String lozinka);

    Boolean existsKorisnikByKorisnickoIme(String username);


}
