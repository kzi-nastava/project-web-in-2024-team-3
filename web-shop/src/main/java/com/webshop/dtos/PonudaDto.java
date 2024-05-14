package com.webshop.dtos;

import com.webshop.model.Kupac;

public class PonudaDto {

    private Long id;

    private double cena;

    private Kupac kupac;

    public PonudaDto(Long id, double cena, Kupac kupac) {
        this.id = id;
        this.cena = cena;
        this.kupac = kupac;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }
}
