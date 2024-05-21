package com.webshop.model;

import com.webshop.dtos.PrijavaDto;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
public class Prijava implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String razlogZaPrijavu;
    @Column
    private LocalDate datumPrijave;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Korisnik podneoPrijavu;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Korisnik odnosiPrijava;
    @Column
    @Enumerated(EnumType.STRING)
    private STATUS status;

    public Prijava(Long id, String razlogZaPrijavu, LocalDate datumPrijave, Korisnik podneoPrijavu, Korisnik odnosiPrijava, STATUS status) {
        this.id = id;
        this.razlogZaPrijavu = razlogZaPrijavu;
        this.datumPrijave = datumPrijave;
        this.podneoPrijavu = podneoPrijavu;
        this.odnosiPrijava = odnosiPrijava;
        this.status = status;
    }

    public Prijava(PrijavaDto prijavaDto) {
        this.razlogZaPrijavu = razlogZaPrijavu;
        this.status = status.PODNETA;
        this.datumPrijave = LocalDate.now();
    }

    public Prijava() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazlogZaPrijavu() {
        return razlogZaPrijavu;
    }

    public void setRazlogZaPrijavu(String razlogZaPrijavu) {
        this.razlogZaPrijavu = razlogZaPrijavu;
    }

    public LocalDate getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(LocalDate datumPrijave) {
        this.datumPrijave = datumPrijave;
    }

    public Korisnik getPodneoPrijavu() {
        return podneoPrijavu;
    }

    public void setPodneoPrijavu(Korisnik podneoPrijavu) {
        this.podneoPrijavu = podneoPrijavu;
    }

    public Korisnik getOdnosiPrijava() {
        return odnosiPrijava;
    }

    public void setOdnosiPrijava(Korisnik odnosiPrijava) {
        this.odnosiPrijava = odnosiPrijava;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
