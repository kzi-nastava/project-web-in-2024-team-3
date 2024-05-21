package com.webshop.model;

import com.webshop.dtos.RegisterDto;
import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role",discriminatorType = DiscriminatorType.STRING)


@DiscriminatorValue(value = "PRODAVAC")
public class Prodavac extends Korisnik{

    @OneToMany
    @JoinColumn
    private Set<Proizvod> proizvodiNaProdaju = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Set<Recenzija> prodavacRecenzije = new HashSet<>(0);

    @Column
    private double prosecnaOcena;

    public Prodavac(RegisterDto registerDto) {
        super();
    }

    public Prodavac() {

    }

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

    public void ukloniProizvodNaProdaju(Proizvod proizvod) {
        this.proizvodiNaProdaju.remove(proizvod);
    }
}
