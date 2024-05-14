package com.webshop.dtos;

import com.webshop.model.Korisnik;

public class RecenzijaDto {

    private Long id;

    private double ocena;

    private String komentar;

    private String datumRecenzije;

    private Korisnik korisnik;

    public RecenzijaDto(Long id, double ocena, String komentar, String datumRecenzije, Korisnik korisnik) {
        this.id = id;
        this.ocena = ocena;
        this.komentar = komentar;
        this.datumRecenzije = datumRecenzije;
        this.korisnik = korisnik;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getDatumRecenzije() {
        return datumRecenzije;
    }

    public void setDatumRecenzije(String datumRecenzije) {
        this.datumRecenzije = datumRecenzije;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
