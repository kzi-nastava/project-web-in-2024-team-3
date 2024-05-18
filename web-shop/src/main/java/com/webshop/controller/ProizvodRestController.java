package com.webshop.controller;

import com.webshop.dtos.ProizvodDto;
import com.webshop.model.Kategorija;
import com.webshop.model.Proizvod;
import com.webshop.model.TIP;
import com.webshop.service.ProizvodService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class ProizvodRestController {

    @Autowired
    private ProizvodService proizvodService;

    @GetMapping("/api/proizvodi")
    public ResponseEntity<List<ProizvodDto>> getProizvodi() {

        List<ProizvodDto> dtos = new ArrayList<>();

        List<Proizvod> proizvodList = proizvodService.findAll();
        for(Proizvod proizvod : proizvodList) {
            ProizvodDto dto = new ProizvodDto(proizvod);
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/api/proizvodi/{id}")
    public ResponseEntity<ProizvodDto> getProizvod(@PathVariable(name = "id") Long id) {
        Proizvod proizvod = proizvodService.pronadjiPoId(id);

        ProizvodDto dto = new ProizvodDto(proizvod);
        return ResponseEntity.ok(dto);
    }

    //promeni da bude po kategorijaNazivu

    @GetMapping(value = "/api/proizvodi/pretraga", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProizvodDto>> pretraga(@RequestParam String upit,@RequestParam double min_cena,@RequestParam double max_cena,@RequestParam TIP tip,@RequestParam Kategorija kategorija) {
        List<Proizvod> proizvodi;

        //U zavisnosti od unosa korisnika naci ce proizvode koji se poklapaju sa svim parametrima

        proizvodi = proizvodService.pronadjiPoNazivuIOpisu(upit);
        List<ProizvodDto> dtos = new ArrayList<>();

        if(min_cena != 0 && max_cena != 0 && min_cena < max_cena) {
            List<Proizvod> poCeni = proizvodService.pronadjiPoCeni(min_cena,max_cena);
            proizvodi.retainAll(poCeni);
        }

        if(tip != null) {
            List<Proizvod> poTipuAukcije = proizvodService.pronadjiPoTipu(tip);
            proizvodi.retainAll(poTipuAukcije);
        }

        if(kategorija != null) {
            List<Proizvod> poKategoriji = proizvodService.pronadjiPoKategoriji(kategorija);
            proizvodi.retainAll(poKategoriji);
        }

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



}
