package com.webshop.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

enum STATUS {PODENTA,ODBIJENA,PRIHVACENA}
@Entity
public class Prijava implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String razlog_za_prijavu;
    @Column
    private Date datum_prijave;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Korisnik podneo_prijavu;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Korisnik odnosi_prijava;
    @Column
    @Enumerated(EnumType.STRING)
    private STATUS status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazlog_za_prijavu() {
        return razlog_za_prijavu;
    }

    public void setRazlog_za_prijavu(String razlog_za_prijavu) {
        this.razlog_za_prijavu = razlog_za_prijavu;
    }

    public Date getDatum_prijave() {
        return datum_prijave;
    }

    public void setDatum_prijave(Date datum_prijave) {
        this.datum_prijave = datum_prijave;
    }

    public Korisnik getPodneo_prijavu() {
        return podneo_prijavu;
    }

    public void setPodneo_prijavu(Korisnik podneo_prijavu) {
        this.podneo_prijavu = podneo_prijavu;
    }

    public Korisnik getOdnosi_prijava() {
        return odnosi_prijava;
    }

    public void setOdnosi_prijava(Korisnik odnosi_prijava) {
        this.odnosi_prijava = odnosi_prijava;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
