package com.webshop.dtos;

import com.webshop.model.Korisnik;
import com.webshop.model.Proizvod;
import com.webshop.model.Recenzija;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class KupacDto {

    private double prosecnaOcena;

    private Long id;

    private String ime;

    private String prezime;

    private String korisnickoIme;

    private String email;

    private String brojTelefona;

    private String lozinka;

    private Date datumRodjenja;

    private String profilnaSlika;

    private String opis;

    private boolean blokiran;

    private Set<Proizvod> kupljeni;

    private Set<Recenzija> recenzije;


    public KupacDto(Korisnik korisnik, double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
        this.id = korisnik.getId();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.korisnickoIme = korisnik.getKorisnickoIme();
        this.email = korisnik.getEmail();
        this.brojTelefona = korisnik.getBrojTelefona();
        this.lozinka = korisnik.getLozinka();
        this.datumRodjenja = korisnik.getDatumRodjenja();
        this.profilnaSlika = korisnik.getProfilnaSlika();
        this.opis = korisnik.getOpis();
        this.blokiran = korisnik.isBlokiran();
    }

    public KupacDto(double prosecnaOcena, Long id, String ime, String prezime, String korisnickoIme, String email, String brojTelefona, String lozinka, Date datumRodjenja, String profilnaSlika, String opis, boolean blokiran, Set<Proizvod> kupljeni) {
        this.prosecnaOcena = prosecnaOcena;
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.email = email;
        this.brojTelefona = brojTelefona;
        this.lozinka = lozinka;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.blokiran = blokiran;
        this.kupljeni = kupljeni;
    }

    public KupacDto(Korisnik korisnik, double prosecnaOcena, Set<Proizvod> kupljeni, Set<Recenzija> recenzije) {
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
        this.kupljeni = kupljeni;
        this.recenzije = recenzije;
    }

    public Set<Recenzija> getRecenzije() {
        return recenzije;
    }

    public void setRecenzije(Set<Recenzija> recenzije) {
        this.recenzije = recenzije;
    }

    public Set<Proizvod> getKupljeni() {
        return kupljeni;
    }

    public void setKupljeni(Set<Proizvod> kupljeni) {
        this.kupljeni = kupljeni;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
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

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
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

    public boolean isBlokiran() {
        return blokiran;
    }

    public void setBlokiran(boolean blokiran) {
        this.blokiran = blokiran;
    }
}
