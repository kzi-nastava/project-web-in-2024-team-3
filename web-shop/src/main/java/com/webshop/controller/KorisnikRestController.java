package com.webshop.controller;

import com.webshop.dtos.KorisnikDto;
import com.webshop.dtos.LoginDto;
import com.webshop.dtos.RegisterDto;
import com.webshop.model.Korisnik;
import com.webshop.service.KorisnikService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KorisnikRestController {

    @Autowired
    KorisnikService korisnikService;

    @PostMapping("/api/registracija")
    public ResponseEntity<String> registracija(@RequestBody RegisterDto registerDto, @RequestParam String uloga, HttpSession session) {

        if(registerDto.getIme().isEmpty() || registerDto.getKorisnickoIme().isEmpty() || registerDto.getBrojTelefona().isEmpty()
                || registerDto.getEmail().isEmpty() || registerDto.getPrezime().isEmpty() || registerDto.getLozinka().isEmpty() || registerDto.getUloga() != "KUPAC" || registerDto.getUloga() != "PRODAVAC") {

            return new ResponseEntity("Neispravno uneti podaci.", HttpStatus.BAD_REQUEST);
        }

        if(registerDto.getEmail().equals(korisnikService.pronadjiMejl(registerDto.getEmail())) ) {
            return new ResponseEntity("Email vec postoji", HttpStatus.BAD_REQUEST);
        }

        if(registerDto.getKorisnickoIme().equals(korisnikService.pronadjiKorisnickoIme(registerDto.getKorisnickoIme()))) {
            return new ResponseEntity("Korisnicko ime vec postoji", HttpStatus.BAD_REQUEST);
        }

        if(registerDto.getLozinka() != registerDto.getPotvrdeLozinke()) {
            return  new ResponseEntity("Nepodudaranje sifre", HttpStatus.BAD_REQUEST);
        }

        if(!registerDto.getUloga().equals("KUPAC") || !registerDto.getUloga().equals("PRODAVAC")) {
            return new ResponseEntity("Uloga nije u opticaju", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Uspesna registracija", HttpStatus.OK);
    }

    public ResponseEntity<String> login (@RequestBody LoginDto loginDto,HttpSession session) {
        if(loginDto.getKorisnickoIme().isEmpty() || loginDto.getLozinka().isEmpty()) {
            return new ResponseEntity("Pogresno uneti podaci.",HttpStatus.BAD_REQUEST);
        }

        Korisnik ulogovan = korisnikService.login(loginDto.getKorisnickoIme(),loginDto.getLozinka());
        if(ulogovan == null) {
            return new ResponseEntity("Ne postoji korisnik!",HttpStatus.NOT_FOUND);
        }

        session.setAttribute("korisnickoIme", ulogovan.getKorisnickoIme());
        session.setAttribute("lozinka",ulogovan.getLozinka());
        return ResponseEntity.ok("Uspesno ulogovan!");
    }
}
