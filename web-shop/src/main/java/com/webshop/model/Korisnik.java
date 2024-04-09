package com.webshop.model;

import jakarta.persistence.*;
import org.intellij.lang.annotations.Pattern;


import java.io.Serializable;
import java.util.Date;


enum Uloga {
    KUPAC,
    PRODAVAC,
    ADMINISTRATOR
}

@Entity
public class Korisnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ime;

    @Column
    private String prezime;

    @Column (unique = true)
    private String korisnickoIme;

    @Column (unique = true)
    private String email;

    @Column
    private String brojTelefona;

    @Column
    private String lozinka;

    @Column
    private Date datumRodjenja;

    @Column
    private String profilnaSlika;

    @Column
    private String opis;

    @Column
    private boolean blokiran;

    @Column
    @Enumerated(EnumType.STRING)
    private  Uloga uloga;


}
