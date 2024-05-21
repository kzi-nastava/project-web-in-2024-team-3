package com.webshop.controller;

import com.webshop.dtos.ProizvodDto;
import com.webshop.model.*;
import com.webshop.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class ProizvodRestController {

    @Autowired
    private ProizvodService proizvodService;

    @Autowired
    KorisnikService korisnikService;

    @Autowired
    KupacService kupacService;

    @Autowired
    ProdavacService prodavacService;

    @Autowired
    PonudaService ponudaService;

    @GetMapping("/api/proizvodi")
    public ResponseEntity<List<ProizvodDto>> getProizvodi(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        List<ProizvodDto> dtos = new ArrayList<>();
        Page<Proizvod> proizvodi = proizvodService.getProizvodLista(page, size);

        if (!proizvodi.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (Proizvod proizvod : proizvodi) {
            dtos.add(new ProizvodDto(proizvod));
        }

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/api/proizvodi/{id}")
    public ResponseEntity<ProizvodDto> getProizvod(@PathVariable(name = "id") Long id) {
        Proizvod proizvod = proizvodService.pronadjiPoId(id);

        if (proizvod == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ProizvodDto dto = new ProizvodDto(proizvod);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/api/proizvod/pretraga")
    public ResponseEntity<List<ProizvodDto>> pretragaPoNazivuIOpisu(@RequestParam(required = false) String naziv, @RequestParam(required = false) String opis) {
        List<Proizvod> proizvodi = proizvodService.pronadjiPoNazivuIOpisu(naziv, opis);
        List<ProizvodDto> dtos = new ArrayList<>();

        for(Proizvod proizvod : proizvodi) {
            ProizvodDto dto = new ProizvodDto(proizvod);
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);

    }

    @GetMapping(value = "/api/proizvodi/filtrirajProizvode")
    public ResponseEntity<List<ProizvodDto>> getProizvodiByFilter(@RequestParam(required = false) Double min, @RequestParam(required = false) Double max, @RequestParam(required = false) TIP tip, @RequestParam(required = false) String kategorija) {
        List<Proizvod> proizvodi = proizvodService.filtrirajProizvod(min, max, tip, kategorija);
        List<ProizvodDto> dtos = new ArrayList<>();

        for(Proizvod proizvod : proizvodi) {
            ProizvodDto dto = new ProizvodDto(proizvod);
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);


    }

    @GetMapping("/api/kategorije/{id}/proizvodi")
    public ResponseEntity<List<ProizvodDto>> pretragaPoKategoriji(@PathVariable(name = "id") Long id) {
        List<Proizvod> proizvodi = proizvodService.pronadjiPoKategoriji(id);

        List<ProizvodDto> dtos = new ArrayList<>();

        for(Proizvod proizvod : proizvodi) {
            ProizvodDto dto = new ProizvodDto(proizvod);
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/api/proizvod-fiskna/{idProizvod}")
    public ResponseEntity<?> kupovinaProizovdaFiksna(@PathVariable Long idProizvod, HttpSession session) {

        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");
        if(ulogovan == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan!", HttpStatus.BAD_REQUEST);
        }

        if(ulogovan.getUloga() != Uloga.KUPAC) {
            return new ResponseEntity<>("Korisnik nije kupac!", HttpStatus.FORBIDDEN);
        }

        Optional<Kupac> ulogovanKupacOptional = Optional.ofNullable(kupacService.findOne(ulogovan.getId()));
        Optional<Proizvod> proizvodOptional = Optional.ofNullable(proizvodService.pronadjiPoId(idProizvod));

        if(proizvodOptional.isEmpty()) {

            return new ResponseEntity<>("Ne postoji proizvod!", HttpStatus.NOT_FOUND);
        }

        Proizvod proizvod = proizvodOptional.get();

        if(proizvod.jeProdat()) {
            return new ResponseEntity<>("Proizvod je prodat!", HttpStatus.BAD_REQUEST);
        }

        Kupac ulogovanKupac = ulogovanKupacOptional.get();
        Optional<Prodavac> prodavacOptional = Optional.ofNullable(prodavacService.findOne(proizvod.getProdavac().getId()));

        if(proizvod.getTipProdaje() != TIP.FIKSNACENA) {
            return new ResponseEntity<>("Proizvod nema fiksnu cenu!", HttpStatus.BAD_REQUEST);
        }

        if(proizvod.getProdavac() == null) {
            return new ResponseEntity<>("Proizvod nema prodavca!", HttpStatus.NOT_FOUND);
        }

        Prodavac prodavac = prodavacOptional.get();
        prodavac.ukloniProizvodNaProdaju(proizvod);
        proizvod.setProdat(true);
        ulogovanKupac.dodajKupljeniProizvod(proizvod);
        korisnikService.saveKorisnik(ulogovanKupac);
        korisnikService.saveKorisnik(prodavac);
        proizvodService.saveProizvod(proizvod);

        //Fali slanje Email-a

        return new ResponseEntity<>("Proizvod je prodat!", HttpStatus.OK);

    }

    @PostMapping("/api/proizvod-aukcija/{idProizvod}")
    public ResponseEntity<?> kupovinaProizvodaAukcija(@PathVariable Long idProizvod, @RequestParam double ponuda, HttpSession session){
        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");
        if(ulogovan == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan!", HttpStatus.BAD_REQUEST);
        }

        if(ulogovan.getUloga() != Uloga.KUPAC) {
            return new ResponseEntity<>("Korisnik nije kupac!", HttpStatus.FORBIDDEN);
        }

        Optional<Kupac> ulogovanKupacOptional = Optional.ofNullable(kupacService.findOne(ulogovan.getId()));
        Optional<Proizvod> proizvodOptional = Optional.ofNullable(proizvodService.pronadjiPoId(idProizvod));

        if(proizvodOptional.isEmpty()) {

            return new ResponseEntity<>("Ne postoji proizvod!", HttpStatus.NOT_FOUND);
        }

        Proizvod proizvod = proizvodOptional.get();

        if(proizvod.jeProdat()) {
            return new ResponseEntity<>("Proizvod je prodat!", HttpStatus.BAD_REQUEST);
        }

        if(proizvod.getProdavac() == null) {
            return new ResponseEntity<>("Proizvod nema prodavca!", HttpStatus.NOT_FOUND);
        }

        if(proizvod.getTipProdaje() != TIP.AUKCIJA) {
            return new ResponseEntity<>("Proizvod nije na aukciji!", HttpStatus.BAD_REQUEST);
        }

        if(ponuda < proizvod.getCena()) {
            return new ResponseEntity<>("Ponuda je preniska!", HttpStatus.BAD_REQUEST);
        }


        Ponuda najvecaPonuda = ponudaService.findTopByProizvodOrderByCenaDesc(proizvod);
        if(najvecaPonuda != null && ponuda <= najvecaPonuda.getCena()) {

            return new ResponseEntity<>("Ponuda je preniska!", HttpStatus.BAD_REQUEST);
        }

        Kupac ulogovanKupac = ulogovanKupacOptional.get();

        Ponuda novaPonuda = new Ponuda(ponuda, proizvod, ulogovanKupac);
        proizvod.dodajPonudu(novaPonuda);
        ponudaService.savePonuda(novaPonuda);

        return new ResponseEntity<>("Ponuda je sacuvana!", HttpStatus.OK);
    }





}
