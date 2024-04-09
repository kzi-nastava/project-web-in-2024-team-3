package com.webshop.model;

import jakarta.persistence.*;

import java.util.List;

enum TIP {

    FIKSNACENA,

    AUKCIJA
}

public class Proizvod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column
    private String naziv;

    @Column
    private String opis;

    @Column
    private String slika;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    private Kategorija kategorija;

    @Column
    private String cena;

    @Column
    @Enumerated(EnumType.STRING)
    private TIP tipProdaje;

    @Column
    private String datumObjavljivanja;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    private List<Ponuda> ponude;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    private Korisnik prodavac;

    @Column
    private boolean recenzijaOdKupca;

    @Column
    private boolean recenzijaOdProdavca;

    @Column
    private boolean prodat;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public TIP getTipProdaje() {
        return tipProdaje;
    }

    public void setTipProdaje(TIP tipProdaje) {
        this.tipProdaje = tipProdaje;
    }

    public String getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(String datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public List<Ponuda> getPonude() {
        return ponude;
    }

    public void setPonude(List<Ponuda> ponude) {
        this.ponude = ponude;
    }

    public Korisnik getProdavac() {
        return prodavac;
    }

    public void setProdavac(Korisnik prodavac) {
        this.prodavac = prodavac;
    }

    public boolean isRecenzijaOdKupca() {
        return recenzijaOdKupca;
    }

    public void setRecenzijaOdKupca(boolean recenzijaOdKupca) {
        this.recenzijaOdKupca = recenzijaOdKupca;
    }

    public boolean isRecenzijaOdProdavca() {
        return recenzijaOdProdavca;
    }

    public void setRecenzijaOdProdavca(boolean recenzijaOdProdavca) {
        this.recenzijaOdProdavca = recenzijaOdProdavca;
    }

    public boolean isProdat() {
        return prodat;
    }

    public void setProdat(boolean prodat) {
        this.prodat = prodat;
    }
}
