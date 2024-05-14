package com.webshop.dtos;

import com.webshop.model.Korisnik;

public class PrijavaDto {

    private long id;

    private String razlogZaPrijavu;

    private String datumPrijave;

    private Korisnik podneoPrijavu;

    private Korisnik odnosiPrijava;

    public PrijavaDto(long id, String razlogZaPrijavu, String datumPrijave, Korisnik podneoPrijavu, Korisnik odnosiPrijava) {
        this.id = id;
        this.razlogZaPrijavu = razlogZaPrijavu;
        this.datumPrijave = datumPrijave;
        this.podneoPrijavu = podneoPrijavu;
        this.odnosiPrijava = odnosiPrijava;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRazlogZaPrijavu() {
        return razlogZaPrijavu;
    }

    public void setRazlogZaPrijavu(String razlogZaPrijavu) {
        this.razlogZaPrijavu = razlogZaPrijavu;
    }

    public String getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(String datumPrijave) {
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
}
