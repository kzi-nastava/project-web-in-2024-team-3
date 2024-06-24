package com.webshop.dtos;

import com.webshop.model.Korisnik;
import com.webshop.model.Proizvod;
import com.webshop.model.Recenzija;

import java.time.LocalDate;
import java.util.Set;

public class ProdavacDto {

    private Long id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String email;
    private String brojTelefona;
    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private double prosecnaOcena;
    private Set<Proizvod> naProdaju;

    private Set<Recenzija> recenzije;

    public ProdavacDto() {
    }

    public ProdavacDto(Korisnik korisnik, double prosecnaOcena, Set<Proizvod>naProdaju, Set<Recenzija> recenzije) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.email = email;
        this.brojTelefona = brojTelefona;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.prosecnaOcena = prosecnaOcena;
        this.naProdaju = naProdaju;
        this.recenzije = recenzije;
    }
}
