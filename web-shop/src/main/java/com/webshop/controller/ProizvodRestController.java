package com.webshop.controller;

import com.webshop.dtos.ProizvodDto;
import com.webshop.model.Proizvod;
import com.webshop.model.TIP;
import com.webshop.service.ProizvodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ProizvodRestController {

    @Autowired
    private ProizvodService proizvodService;

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





}
