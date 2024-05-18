package com.webshop.dtos;

import com.webshop.model.Proizvod;

public class ProizvodDto {

    private Long id;

    private String naziv;

    private String opis;

    private double cena;

    public ProizvodDto(Long id, String naziv, String opis, double cena) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
    }

    public ProizvodDto(Proizvod proizvod) {
        this.id = proizvod.getId();
        this.naziv = proizvod.getNaziv();
        this.opis = proizvod.getOpis();
        this.cena = proizvod.getCena();


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
}
