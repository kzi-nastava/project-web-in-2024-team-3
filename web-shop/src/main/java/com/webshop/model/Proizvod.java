package com.webshop.model;

import jakarta.persistence.*;

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
    private Date datumObjavljivanja;

    @OneToMany
    @JoinColumn
    private Set<Ponuda> ponudeZaProizvod = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
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

    public Date getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(Date datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }


}
