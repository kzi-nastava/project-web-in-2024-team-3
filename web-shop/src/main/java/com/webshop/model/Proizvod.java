package com.webshop.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
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
    @JoinColumn
    private Kategorija kategorija;

    @Column
    private double cena;

    @Column
    @Enumerated(EnumType.STRING)
    private TIP tipProdaje;

    @Column
    private LocalDate datumObjavljivanja;

    @OneToMany
    @JoinColumn
    private Set<Ponuda> ponudeZaProizvod = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Prodavac prodavac;

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    @Column
    private boolean recenzijaOdKupca;

    @Column
    private boolean recenzijaOdProdavca;

    @Column
    private boolean prodat;

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

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public TIP getTipProdaje() {
        return tipProdaje;
    }

    public void setTipProdaje(TIP tipProdaje) {
        this.tipProdaje = tipProdaje;
    }

    public LocalDate getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(LocalDate datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }


    public boolean isProdat() {
        return prodat;
    }

    public void setProdat(boolean prodat) {
        this.prodat = prodat;
    }
    

    public Set<Ponuda> getPonudeZaProizvod() {
        return ponudeZaProizvod;
    }

    public void setPonudeZaProizvod(Set<Ponuda> ponudeZaProizvod) {
        this.ponudeZaProizvod = ponudeZaProizvod;
    }

    public boolean jeProdat() {

        if(prodat) {
            return true;
        }

        return  false;
    }

    public void dodajPonudu(Ponuda ponuda) {
        this.ponudeZaProizvod.add(ponuda);
    }
}
