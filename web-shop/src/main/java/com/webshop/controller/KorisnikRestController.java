package com.webshop.controller;

import com.webshop.dtos.KorisnikDto;
import com.webshop.dtos.RegisterDto;
import com.webshop.model.Korisnik;
import com.webshop.service.KorisnikService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KorisnikRestController {

    KorisnikService korisnikService;

    public ResponseEntity<String> registracija(@RequestBody RegisterDto registerDto, @RequestAttribute String user_role, HttpSession session) {
        registerDto.setUloga(user_role);
        if(registerDto.getIme().isEmpty() || registerDto.getKorisnickoIme().isEmpty() || registerDto.getBrojTelefona().isEmpty()
                || registerDto.getEmail().isEmpty() || registerDto.getPrezime().isEmpty() || registerDto.getLozinka().isEmpty()) {

            return new ResponseEntity("Neispravno uneti podaci.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Uspesna registracija", HttpStatus.OK);
    }
}
