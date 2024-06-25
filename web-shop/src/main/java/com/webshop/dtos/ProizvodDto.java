package com.webshop.dtos;

import com.webshop.model.Kategorija;
import com.webshop.model.Proizvod;
import com.webshop.model.TIP;

public class ProizvodDto {

    private Long id;

    private String naziv;

    private String opis;

    private double cena;

    private Kategorija kategorija;

    private String slika;

    private TIP tipProdaje;

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

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public ProizvodDto() {
    }

    public ProizvodDto(Long id, String naziv, String opis, double cena) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
    }

    public ProizvodDto(double cena, Kategorija kategorija, TIP tipProdaje) {
        this.cena = cena;
        this.kategorija = kategorija;
        this.tipProdaje = tipProdaje;
    }

    public ProizvodDto(Proizvod proizvod){
        this.id = proizvod.getId();
        this.naziv = proizvod.getNaziv();
        this.opis = proizvod.getOpis();
        this.cena = proizvod.getCena();
        this.slika = proizvod.getSlika();
        this.tipProdaje = proizvod.getTipProdaje();
        this.kategorija = proizvod.getKategorija();
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
