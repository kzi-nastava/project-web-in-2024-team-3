package com.webshop.controller;

import com.webshop.dtos.PrijavaDto;
import com.webshop.model.Korisnik;
import com.webshop.model.Prijava;
import com.webshop.model.STATUS;
import com.webshop.model.Uloga;
import com.webshop.service.KorisnikService;
import com.webshop.service.PrijavaService;
import com.webshop.service.ProizvodService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.webshop.model.STATUS.*;
import static com.webshop.model.Uloga.*;


@RestController
public class PrijavaRestController {

    @Autowired
    private PrijavaService prijavaService;

    @Autowired
    KorisnikService korisnikService;

    @Autowired
    ProizvodService proizvodService;

    @PostMapping("/api/odbij-prijavu/{id}")
    public ResponseEntity<?> odbijPrijavu(@PathVariable Long id, HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        if(korisnik == null){
            return new ResponseEntity<>("Nema prijavljenog korisnikai!", HttpStatus.FORBIDDEN);
        }
        if(korisnik.getUloga() != ADMIN) {
            return new ResponseEntity<>("Nemate ovlascenje!", HttpStatus.FORBIDDEN);
        }

        Prijava prijava = prijavaService.getPrijavaById(id);
        if(prijava == null) {
            return new ResponseEntity<>("Ne postoji prijava!", HttpStatus.NOT_FOUND);
        }
        if(prijava.getStatus() != PODNETA) {
            return new ResponseEntity<>("Prijava nije podneta!", HttpStatus.BAD_REQUEST);
        }

        prijava.setStatus(ODBIJENA);
        prijavaService.savePrijava(prijava);
        return new ResponseEntity<>("Prijava odbijena!", HttpStatus.OK);

    }

    @PostMapping("/api/prihvati-prijavu/{id}")
    public ResponseEntity<?> prihvatiPrijavu(@PathVariable Long id, HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        if(korisnik == null) {
            return new ResponseEntity<>("Nema prijavljenog korisnika!", HttpStatus.FORBIDDEN);
        }
        if(korisnik.getUloga() != ADMIN){
            return new ResponseEntity<>("Nemate ovlascenje!", HttpStatus.FORBIDDEN);
        }
        Prijava prijava = prijavaService.getPrijavaById(id);
        if(prijava == null) {
            return new ResponseEntity<>("Ne postoji prijava!", HttpStatus.NOT_FOUND);
        }
        if(prijava.getStatus() != PODNETA) {
            return new ResponseEntity<>("Prijava nije podneta", HttpStatus.BAD_REQUEST);
        }
        prijava.setStatus(PRIHVACENA);
        prijavaService.savePrijava(prijava);

        if(prijava.getOdnosiPrijava().getUloga() == PRODAVAC) {
            Korisnik prodavac = korisnikService.pronadjiKupcaPoId(prijava.getOdnosiPrijava().getId());
            prodavac.setBlokiran(true);
            proizvodService.obrisiProizvodPoIdProdavca(prodavac.getId());
            korisnikService.saveKorisnik(prodavac);
            return new ResponseEntity<>("Prijava prihvacena!", HttpStatus.OK);
        }
        prijava.getOdnosiPrijava().setBlokiran(true);
        korisnikService.saveKorisnik(prijava.getOdnosiPrijava());
        return new ResponseEntity<>("Prijava prihvacena!", HttpStatus.OK);
    }

    @PostMapping("/api/prijavi-prodavca/{id}")
    public ResponseEntity<?> prijaviProdavca(@PathVariable Long id, @RequestBody PrijavaDto razlogPrijave, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (loggedKorisnik == null) {
            return new ResponseEntity<>("Nema prijavljenog korisnika!", HttpStatus.FORBIDDEN);
        }
        if (loggedKorisnik.getUloga() != Uloga.KUPAC) {
            return new ResponseEntity<>("Niste kupac!", HttpStatus.FORBIDDEN);
        }

        Prijava prijavaProfila = prijavaService.prijaviProdavca(id, razlogPrijave, loggedKorisnik);
        if (prijavaProfila == null) {
            return new ResponseEntity<>("Neuspesna prijava!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prijavaProfila, HttpStatus.OK);
    }

    @PostMapping("/api/prijavi-kupca/{id}")
    public ResponseEntity<?> prijaviKupca(@PathVariable Long id, @RequestBody PrijavaDto razlogPrijave, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (loggedKorisnik == null) {
            return new ResponseEntity<>("Nema prijavljenog korisnika!", HttpStatus.FORBIDDEN);
        }
        if (loggedKorisnik.getUloga() != PRODAVAC) {
            return new ResponseEntity<>("Niste prodavac!", HttpStatus.FORBIDDEN);
        }

        Prijava prijavaProfila = prijavaService.prijaviProdavca(id, razlogPrijave, loggedKorisnik);
        if (prijavaProfila == null) {
            return new ResponseEntity<>("Neuspesna prijava!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prijavaProfila, HttpStatus.OK);
    }


}
