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



    @Column
    private Kupac kupac;



}
