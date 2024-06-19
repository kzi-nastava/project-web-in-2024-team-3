package com.webshop.dtos;

import com.webshop.model.Korisnik;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class RecenzijaDto {

    private Long id;

    private double ocena;

    private String komentar;

    private LocalDate datumRecenzije;

    private Korisnik korisnik;

    public RecenzijaDto(Long id, int ocena, String komentar, LocalDate datumRecenzije) {
        this.id = id;
        this.ocena = ocena;
        this.komentar = komentar;
        this.datumRecenzije = datumRecenzije;

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

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public LocalDate getDatumRecenzije() {
        return datumRecenzije;
    }

    public void setDatumRecenzije(LocalDate datumRecenzije) {
        this.datumRecenzije = datumRecenzije;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
