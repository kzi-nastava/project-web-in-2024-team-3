package com.webshop.dtos;

import com.webshop.model.Kategorija;
import com.webshop.model.TIP;

import java.io.Serializable;

public class PostavljanjeProdajeDto implements Serializable {

    private String naziv;
    private String opis;
    private double cena;
    private String slika;
    private TIP tipProdaje;
    private Kategorija kategorija;

    public PostavljanjeProdajeDto(String naziv, String opis, double cena, String slika, TIP tipProdaje, Kategorija kategorija) {
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.slika = slika;
        this.tipProdaje = tipProdaje;
        this.kategorija = kategorija;
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

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public TIP getTipProdaje() {
        return tipProdaje;
    }

    public void setTipProdaje(TIP tipProdaje) {
        this.tipProdaje = tipProdaje;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }
}
