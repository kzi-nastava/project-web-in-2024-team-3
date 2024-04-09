package com.webshop.model;

import jakarta.persistence.Column;

import java.util.List;

public class Prodavac extends Korisnik{

    @Column
    private List<Proizvod> proizvodiNaProdaju;

    @Column
    private List<Recenzija> recenzije;

    @Column
    private double prosecnaOcena;

    public List<Proizvod> getProizvodiNaProdaju() {
        return proizvodiNaProdaju;
    }

    public void setProizvodiNaProdaju(List<Proizvod> proizvodiNaProdaju) {
        this.proizvodiNaProdaju = proizvodiNaProdaju;
    }

    public List<Recenzija> getRecenzije() {
        return recenzije;
    }

    public void setRecenzije(List<Recenzija> recenzije) {
        this.recenzije = recenzije;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }
}
