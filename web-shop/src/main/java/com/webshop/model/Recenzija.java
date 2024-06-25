package com.webshop.model;

import com.webshop.dtos.RecenzijaDto;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Recenzija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double ocena;
    @Column
    private String komentar;
    @Column
    private LocalDate datum;


    @ManyToOne(fetch = FetchType.EAGER)
    private Korisnik korisnik;

    public Recenzija() {
    }

    public Recenzija(RecenzijaDto recenzijaDto,String korisnikIme) {
        this.ocena = recenzijaDto.getOcena();
        this.komentar = recenzijaDto.getKomentar();
        this.datum = recenzijaDto.getDatumRecenzije();

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

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
