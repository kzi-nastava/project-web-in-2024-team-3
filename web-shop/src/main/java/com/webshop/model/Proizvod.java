package com.webshop.model;

import jakarta.persistence.*;

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

    @Column
    private String kategorija;

    @Column
    private String cena;

    @Column
    @Enumerated(EnumType.STRING)
    private TIP tipProdaje;

    @Column
    private String datumObjavljivanja;

    //private List<Ponuda> ponude;

    @Column
    private Korisnik prodavac;

    @Column
    private boolean recenzijaOdKupca;

    @Column
    private boolean recenzijaOdProdavca;

    @Column
    private boolean prodat;

}
