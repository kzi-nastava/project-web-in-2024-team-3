package com.webshop.model;

import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role",discriminatorType = DiscriminatorType.STRING)


@DiscriminatorValue(value = "PRODAVAC")
public class Prodavac extends Korisnik{

    @OneToMany
    @JoinTable(name = "proizvodiNaProdaju",
            joinColumns = @JoinColumn(name = "prodavac_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "proizvod_id", referencedColumnName = "id"))
    private Set<Proizvod> proizvodiNaProdaju = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    private Set<Recenzija> prodavacRecenzije = new HashSet<>(0);

    @Column
    private double prosecnaOcena;

    public Set<Proizvod> getProizvodiNaProdaju() {
        return proizvodiNaProdaju;
    }

    public void setProizvodiNaProdaju(Set<Proizvod> proizvodiNaProdaju) {
        this.proizvodiNaProdaju = proizvodiNaProdaju;
    }



    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }
}
