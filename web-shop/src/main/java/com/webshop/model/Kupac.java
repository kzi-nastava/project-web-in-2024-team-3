package com.webshop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class Kupac extends Korisnik{


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    private List<Proizvod> kupljeniProizvodi;


    @Column
    private double prosecnaOcena;

    @Column
    private List<Recenzija> recenzije = new ArrayList<>();



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Ponuda ponuda;

    public List<Proizvod> getKupljeniProizvodi() {
        return kupljeniProizvodi;
    }

    public void setKupljeniProizvodi(List<Proizvod> kupljeniProizvodi) {
        this.kupljeniProizvodi = kupljeniProizvodi;
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

    public Ponuda getPonuda() {
        return ponuda;
    }

    public void setPonuda(Ponuda ponuda) {
        this.ponuda = ponuda;
    }
}
