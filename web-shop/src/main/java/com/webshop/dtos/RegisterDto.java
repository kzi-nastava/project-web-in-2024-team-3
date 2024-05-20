package com.webshop.dtos;

import com.webshop.model.Uloga;

public class RegisterDto {

    private String ime;

    private String prezime;

    private String korisnickoIme;

    private String email;

    private String brojTelefona;

    private String lozinka;

    private String potvrdeLozinke;

    private Uloga uloga;


    public RegisterDto(String ime, String prezime, String korisnickoIme, String email, String brojTelefona, String lozinka, Uloga uloga) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.email = email;
        this.brojTelefona = brojTelefona;
        this.lozinka = lozinka;
        this.uloga = uloga;
    }

    public String getPotvrdeLozinke() {
        return potvrdeLozinke;
    }

    public void setPotvrdeLozinke(String potvrdeLozinke) {
        this.potvrdeLozinke = potvrdeLozinke;
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

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }
}
