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
    //@OneToOne
    //@Coulmn
    //private Korisnik podneo_prijavu;

    //OneToOne
    //Column
    //private Korisnik odnosi_prijava;
    @Column
    @Enumerated(EnumType.STRING)
    private STATUS status;


}
