package com.webshop.dtos;

import com.webshop.model.Korisnik;
import com.webshop.model.Proizvod;
import com.webshop.model.Recenzija;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class ProdavacDto {

    private Long id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String email;
    private String brojTelefona;
    private Date datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private double prosecnaOcena;
    private Set<Proizvod> naProdaju;

    private Set<Recenzija> recenzije;

    public ProdavacDto() {
    }

    public ProdavacDto(Korisnik korisnik, double prosecnaOcena, Set<Proizvod>naProdaju, Set<Recenzija> recenzije) {
        this.id = korisnik.getId();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.korisnickoIme = korisnik.getKorisnickoIme();
        this.lozinka = korisnik.getLozinka();
        this.email = korisnik.getEmail();
        this.brojTelefona = korisnik.getBrojTelefona();
        this.datumRodjenja = korisnik.getDatumRodjenja();
        this.profilnaSlika = korisnik.getProfilnaSlika();
        this.opis = korisnik.getOpis();
        this.prosecnaOcena = prosecnaOcena;
        this.naProdaju = naProdaju;
        this.recenzije = recenzije;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public Set<Proizvod> getNaProdaju() {
        return naProdaju;
    }

    public void setNaProdaju(Set<Proizvod> naProdaju) {
        this.naProdaju = naProdaju;
    }

    public Set<Recenzija> getRecenzije() {
        return recenzije;
    }

    public void setRecenzije(Set<Recenzija> recenzije) {
        this.recenzije = recenzije;
    }


}
