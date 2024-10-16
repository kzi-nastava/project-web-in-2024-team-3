package com.webshop.model;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
public class Ponuda implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double cena;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Kupac kupac;

    @OneToOne
    private Proizvod proizvod;



    public Ponuda() {

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

    public Ponuda(double cena,Proizvod proizvod, Kupac kupac) {
        this.cena = cena;
        this.proizvod = proizvod;
        this.kupac = kupac;
    }
}
