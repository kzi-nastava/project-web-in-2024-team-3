package com.webshop.controller;

import com.webshop.dtos.RecenzijaDto;
import com.webshop.dtos.RecenzijaProdavcaDto;
import com.webshop.model.Korisnik;
import com.webshop.model.Kupac;
import com.webshop.model.Recenzija;
import com.webshop.model.Uloga;
import com.webshop.service.RecenzijaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecenzijaRestController {

    @Autowired
    private RecenzijaService recenzijaService;

    @PostMapping("/api/oceni-prodavca/{id}")
    public ResponseEntity<?> oceniProdavca(@PathVariable(name = "id") Long prodavacId, @RequestBody RecenzijaDto recenzijaDto, HttpSession session) {
        Korisnik loggedkorisnik = (Korisnik) session.getAttribute("korisnik");
        if(loggedkorisnik == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan.", HttpStatus.UNAUTHORIZED);
        }
        boolean kupio = recenzijaService.razmena(loggedkorisnik.getId(),prodavacId);
        if(kupio) {
            Recenzija recenzija = new Recenzija(recenzijaDto, loggedkorisnik);
            return new ResponseEntity<>(recenzijaService.dodajRecenziju(recenzija, prodavacId, loggedkorisnik.getId()), HttpStatus.OK);
        }

        return new ResponseEntity<>("Ne mozete oceniti prodavca bez kupovine proizvoda",HttpStatus.FORBIDDEN);
    }

    @PostMapping("/api/oceni-kupca/{id}")
    public ResponseEntity<?> oceniKupca(@PathVariable(name = "id") Long kupacId, @RequestBody RecenzijaDto recenzijaDto, HttpSession session) {
        Korisnik loggedkorisnik = (Korisnik) session.getAttribute("korisnik");
        if(loggedkorisnik == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan.", HttpStatus.UNAUTHORIZED);
        }
        boolean kupio = recenzijaService.razmena(kupacId,loggedkorisnik.getId());
        if(kupio) {
            Recenzija recenzija = new Recenzija(recenzijaDto, loggedkorisnik);
            return new ResponseEntity<>(recenzijaService.dodajRecenzijuKupac(recenzija, loggedkorisnik.getId(), kupacId), HttpStatus.OK);
        }

        return new ResponseEntity<>("Ne mozete oceniti kupca ako nije kupio vas proizvod.",HttpStatus.FORBIDDEN);
    }

    @PutMapping("/api/izmeni-recenziju/{id}")
    public ResponseEntity<?> izmeniRecenziju(@PathVariable(name = "id") Long id, @RequestBody RecenzijaDto komentar,HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        if(korisnik == null || korisnik.getUloga() != Uloga.ADMIN) {
            return new ResponseEntity<>("Ne mozete izmeniti recenziju",HttpStatus.UNAUTHORIZED);
        }

        Recenzija recenzija = recenzijaService.findById(id);
        if(recenzija == null) {
            return new ResponseEntity<>("Ne postoji data recenzija!",HttpStatus.NOT_FOUND);
        }
        if(komentar.getKomentar() == null) {
            return new ResponseEntity<>("Niste uneli izmenu recenzije!", HttpStatus.BAD_REQUEST);
        }
        recenzija.setKomentar(komentar.getKomentar());
        recenzijaService.saveRecenzija(recenzija);
        return new ResponseEntity<>("Uspesno izmenjena recenzija", HttpStatus.OK);
    }

    @DeleteMapping("/obrisi-recenziju/{id}")
    public ResponseEntity<?> obrisiRecenziju(@PathVariable Long id, HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        if (korisnik == null || korisnik.getUloga() != Uloga.ADMIN) {
            return new ResponseEntity<>("Ne mozete obrisati recenziju!",HttpStatus.UNAUTHORIZED);
        }
        Recenzija recenzija = recenzijaService.findById(id);
        if(recenzija == null) {
            return new ResponseEntity<>("Ne postoji data recenzija!",HttpStatus.NOT_FOUND);
        }
        recenzijaService.deleteRecenzijaById(id);
        return new ResponseEntity<>("Uspesno obrisana recenzija", HttpStatus.OK);
    }

    @GetMapping("/recenzije-prodavaca")
    public ResponseEntity<?> getRecenzijeProdavaca(HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan!", HttpStatus.FORBIDDEN);
        }

        if (loggedKorisnik.getUloga() != Uloga.KUPAC) {
            return new ResponseEntity<>("Samo kupci mogu pristupiti ovoj funkcionalnosti!", HttpStatus.FORBIDDEN);
        }

        List<RecenzijaProdavcaDto> listaRecenzija = recenzijaService.getRecenzijaList(loggedKorisnik.getId());
        if (listaRecenzija.isEmpty()) {
            return new ResponseEntity<>("Recenzija ne postoji!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaRecenzija, HttpStatus.OK);
        }


    @GetMapping("/recenzije")
    public ResponseEntity<List<Recenzija>> getRecenzije() {
        List<Recenzija> recenzijaList = recenzijaService.getRecenzijaList();

        if(recenzijaList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recenzijaService.getRecenzijaList(), HttpStatus.OK);

    }

    public ResponseEntity<?> getSvojeRecenzije(HttpSession session) {
        Korisnik loggedkorisnik = (Korisnik) session.getAttribute("korisnik");

        if(loggedkorisnik == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan",HttpStatus.FORBIDDEN);
        }

        List<Recenzija> recenzijaList = recenzijaService.getSvojeRecenzije(loggedkorisnik.getId());

        if(recenzijaList.isEmpty()) {
            return new ResponseEntity<>("Recenizja ne postoji!",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(recenzijaList,HttpStatus.OK);
    }
}



