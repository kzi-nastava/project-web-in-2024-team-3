package com.webshop.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role",discriminatorType = DiscriminatorType.STRING)


@DiscriminatorValue(value = "KUPAC")
public class Kupac extends Korisnik{



    @OneToMany
    @JoinTable(name = "kupljeniProizvodi",
            joinColumns = @JoinColumn(name = "kupac_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "proizvod_id", referencedColumnName = "id"))
    private Set<Proizvod> kupljeniProizvodi = new HashSet<>();



    @Column
    private double prosecnaOcena;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    private Set<Recenzija> kupacRecenzije = new HashSet<>();


    public Set<Proizvod> getKupljeniProizvodi() {
        return kupljeniProizvodi;
    }

    public void setKupljeniProizvodi(Set<Proizvod> kupljeniProizvodi) {
        this.kupljeniProizvodi = kupljeniProizvodi;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }


}
