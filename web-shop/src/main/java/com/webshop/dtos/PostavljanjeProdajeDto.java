package com.webshop.dtos;

import com.webshop.model.Kategorija;
import com.webshop.model.TIP;

import java.io.Serializable;

public class PostavljanjeProdajeDto implements Serializable {

    private Long id;

    private String naziv;

    private String opis;

    private double cena;

    private Kategorija kategorija;

    private TIP tipProdaje;

    private String slika;

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

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public TIP getTipProdaje() {
        return tipProdaje;
    }

    public void setTipProdaje(TIP tipProdaje) {
        this.tipProdaje = tipProdaje;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public PostavljanjeProdajeDto(Long id, String naziv, String opis, double cena, Kategorija kategorija, TIP tipProdaje, String slika) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.kategorija = kategorija;
        this.tipProdaje = tipProdaje;
        this.slika = slika;
    }
}
