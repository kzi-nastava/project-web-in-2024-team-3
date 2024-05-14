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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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


    @GetMapping(value = "/api/proizvodi/pretraga", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Proizvod>> pretraga(String upit, double min_cena, double max_cena, TIP tip, Kategorija kategorija) {
        List<Proizvod> proizvodi;

        //U zavisnosti od unosa korisnika naci ce proizvode koji se poklapaju sa svim parametrima

        proizvodi = proizvodService.pronadjiPoNazivuIOpisu(upit);

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

        return new ResponseEntity<>(proizvodi, HttpStatus.OK);

    }


}
