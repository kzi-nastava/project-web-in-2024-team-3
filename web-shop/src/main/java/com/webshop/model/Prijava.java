package com.webshop.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

enum STATUS {PODNETA,ODBIJENA,PRIHVACENA}
@Entity
public class Prijava implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String razlogZaPrijavu;
    @Column
    private Date datumPrijave;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Korisnik podneoPrijavu;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Korisnik odnosiPrijava;
    @Column
    @Enumerated(EnumType.STRING)
    private STATUS status;

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

    public Date getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(Date datumPrijave) {
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
