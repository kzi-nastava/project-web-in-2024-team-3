package com.webshop.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Kategorija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String naziv;

    public Kategorija() {}

    public Kategorija(Kategorija kategorija) {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
