package com.webshop.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Recenzija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int ocena;
    @Column
    private String komentar;
    @Column
    private Date datum;
    //@ManyToOne
    //private Koristnik korisnik;


}
