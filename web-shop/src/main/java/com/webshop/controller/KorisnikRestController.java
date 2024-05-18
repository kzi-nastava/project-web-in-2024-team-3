package com.webshop.controller;

import com.webshop.dtos.*;
import com.webshop.model.Korisnik;
import com.webshop.model.Kupac;
import com.webshop.model.Prodavac;
import com.webshop.model.Uloga;
import com.webshop.repository.KorisnikRepository;
import com.webshop.service.KorisnikService;
import com.webshop.service.KupacService;
import com.webshop.service.ProdavacService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@RestController
public class KorisnikRestController {

    @Autowired
    KorisnikService korisnikService;
    @Autowired
    private KupacService kupacService;
    @Autowired
    private ProdavacService prodavacService;

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

    @PostMapping("api/login")
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



    @PutMapping("/api/azuriraj")
    public ResponseEntity<UpdateDto> azuriraj (@RequestBody UpdateDto updateDto, HttpSession session ) {


        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");
        if(ulogovan == null) {
            return new ResponseEntity("Korisnik nije pronadjen!", HttpStatus.NOT_FOUND);
        } else {

            if(!updateDto.getIme().isEmpty()) {
                ulogovan.setIme(updateDto.getIme());
            }

            if(!updateDto.getPrezime().isEmpty()) {
                ulogovan.setPrezime(updateDto.getPrezime());
            }

            if(!updateDto.getBrojTelefona().isEmpty()) {
                ulogovan.setBrojTelefona(updateDto.getBrojTelefona());
            }

            if(!updateDto.getDatumRodjenja().toString().isEmpty()) {
                ulogovan.setDatumRodjenja(updateDto.getDatumRodjenja());
            }

            if(!updateDto.getProfilnaSlika().isEmpty()) {
                ulogovan.setProfilnaSlika(updateDto.getProfilnaSlika());
            }

            if(!updateDto.getOpis().isEmpty()) {
                ulogovan.setOpis(updateDto.getOpis());
            }

            if(Objects.equals(updateDto.getStaraLozinka(), ulogovan.getLozinka())) {

                if(!updateDto.getNovaLozinka().isEmpty()) {
                    ulogovan.setLozinka(updateDto.getNovaLozinka());
                }

                if(!updateDto.getKorisnickoIme().isEmpty()) {
                    ulogovan.setKorisnickoIme(updateDto.getKorisnickoIme());
                }

                if(!updateDto.getEmail().isEmpty()) {
                    ulogovan.setEmail(updateDto.getEmail());
                }

            } else {
                return new ResponseEntity("Pogresna lozinka", HttpStatus.BAD_REQUEST);
            }


        }

        return new ResponseEntity("Uspesno azuriranje", HttpStatus.OK);
    }

    @GetMapping("/api/korisnik/{id}")
    public ResponseEntity<Korisnik> pregledProfila(@PathVariable(name="id") Long id) {
        Korisnik korisnik = korisnikService.pronadjiPoId(id);

        if(korisnik.getUloga().equals(Uloga.ADMIN) || korisnik == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(korisnik.getUloga().equals(Uloga.KUPAC)) {
            Kupac kupac = kupacService.findOne(id);
            return ResponseEntity.ok(kupac);
        }
        else {
            Prodavac prodavac = prodavacService.findOne(id);
            return ResponseEntity.ok(prodavac);
        }

    }

}
