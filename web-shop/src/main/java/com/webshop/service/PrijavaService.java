package com.webshop.service;

import com.webshop.dtos.PrijavaDto;
import com.webshop.model.*;
import com.webshop.repository.KorisnikRepository;
import com.webshop.repository.PrijavaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PrijavaService {

    @Autowired
    private PrijavaRepository prijavaRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    public Prijava getPrijavaById(Long id) {
        Optional<Prijava> prijavaProfila = prijavaRepository.findById(id);



        return prijavaProfila.orElse(null);
    }

    public void savePrijava(Prijava prijavaProfila) {

        prijavaRepository.save(prijavaProfila);
    }

    public Prijava prijaviProdavca(Long prodavacId, PrijavaDto razlog, Korisnik korisnik) {
        Kupac kupac = (Kupac) korisnikRepository.findById(korisnik.getId()).get();
        Prodavac prodavac = (Prodavac) korisnikRepository.findById(prodavacId).get();

        Set<Proizvod> kupljeniProizvodi = kupac.getKupljeniProizvodi();
        Set<Proizvod> proizvodiNaProdaju = prodavac.getProizvodiNaProdaju();

        boolean kupio = false;
        for (Proizvod kupljeni : kupljeniProizvodi) {
            for (Proizvod naProdaju : proizvodiNaProdaju) {
                if (kupljeni.equals(naProdaju)) {
                    kupio = true;
                    break;
                }
            }
        }

        if (kupio) {
            Prijava prijavaProfila = new Prijava(razlog);
            prijavaProfila.setOdnosiPrijava(prodavac);
            prijavaProfila.setPodneoPrijavu(kupac);
            return prijavaRepository.save(prijavaProfila);
        }
        return null;
    }

    public Prijava prijaviKupca(Long kupacId, PrijavaDto razlog, Korisnik korisnik) {
        Kupac kupac = (Kupac) korisnikRepository.findById(kupacId).get();
        Prodavac prodavac = (Prodavac) korisnikRepository.findById(korisnik.getId()).get();

        Set<Proizvod> kupljeniProizvodi = kupac.getKupljeniProizvodi();
        Set<Proizvod> proizvodiNaProdaju = prodavac.getProizvodiNaProdaju();

        boolean kupio = false;
        for (Proizvod kupljeni : kupljeniProizvodi) {
            for (Proizvod naProdaju : proizvodiNaProdaju) {
                if (kupljeni.equals(naProdaju)) {
                    kupio = true;
                    break;
                }
            }
        }

        if (kupio) {
            Prijava prijavaProfila = new Prijava(razlog);
            prijavaProfila.setOdnosiPrijava(kupac);
            prijavaProfila.setPodneoPrijavu(prodavac);
            return prijavaRepository.save(prijavaProfila);
        }
        return null;




    }
}
