package com.webshop.service;

import com.webshop.dtos.RecenzijaProdavcaDto;
import com.webshop.model.*;
import com.webshop.repository.KorisnikRepository;
import com.webshop.repository.ProdavacRepository;
import com.webshop.repository.ProizvodRepository;
import com.webshop.repository.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ProizvodService proizvodService;
    @Autowired
    private ProdavacRepository prodavacRepository;

    public Recenzija dodajRecenziju(Recenzija recenzija,Long prodavacId, Long kupacId) {
        Proizvod proizvod = proizvodService.pronadjiPoId(prodavacId);
        Prodavac prodavac = proizvod.getProdavac();
        Optional<Korisnik> korisnik = korisnikRepository.findById(kupacId);
        Set<Proizvod> kupljeniProizvodi = new HashSet<>();

        if(korisnik.isPresent()) {
            Korisnik k = korisnik.get();
            if (k instanceof Kupac) {
                Kupac kupac = (Kupac) k;
                kupljeniProizvodi = kupac.getKupljeniProizvodi();
            }
        }

        for (Proizvod p : kupljeniProizvodi) {
            if (kupljeniProizvodi.contains(proizvod)) {
                recenzija.setDatum(LocalDate.now());
                Set<Recenzija> dobijenaRecenzija = prodavac.getProdavacRecenzije();
                dobijenaRecenzija.add(recenzija);
                return recenzijaRepository.save(recenzija);
            }
        }
        return null;
    }

    public void saveRecenzija(Recenzija recenzija) {recenzijaRepository.save(recenzija);}

    public Recenzija findById(Long id) {
        Optional<Recenzija> recenzija = recenzijaRepository.findById(id);
        if(recenzija == null) {
            return null;
        }
        return recenzija.get();
    }

    public void deleteRecenzijaById(Long id) {
        recenzijaRepository.deleteById(id);
    }

    public List<RecenzijaProdavcaDto> getRecenzijaList(Long kupacId) {
        List<Prodavac> prodavci = prodavacRepository.findAllByUloga(Uloga.PRODAVAC);
        List<RecenzijaProdavcaDto> recenzijaProdavcaDtos = new ArrayList<>();

        if(prodavci.isEmpty()) {
            return null;
        }

        for(int i = 0; i < prodavci.size(); i++) {
            Prodavac prodavac = prodavci.get(i);
            for(Recenzija r : prodavac.getProdavacRecenzije()) {
                if(r.getKorisnik().getId().equals(kupacId)) {
                    break;
                }
            }
        }

        for(int i = 0; i < prodavci.size(); i++) {
            Prodavac prodavac = prodavci.get(i);
            for(Recenzija r : prodavac.getProdavacRecenzije()) {
                recenzijaProdavcaDtos.add(new RecenzijaProdavcaDto(r));
            }
        }

        return recenzijaProdavcaDtos;
    }

    public boolean razmena(Korisnik korisnik,Long prodavacId) {
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

        return kupio;
    }

}
