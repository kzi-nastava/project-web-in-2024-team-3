package com.webshop.controller;

import com.webshop.dtos.*;
import com.webshop.model.*;
import com.webshop.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class KorisnikRestController {

    @Autowired
    KorisnikService korisnikService;
    @Autowired
    private KupacService kupacService;
    @Autowired
    private ProdavacService prodavacService;

    @PostMapping("api/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {

        if (korisnikService.existsEmail(dto.getEmail()) || korisnikService.existsKorisnickoIme(dto.getKorisnickoIme())) {
            return new ResponseEntity<>("Korisnik vec postoji!", HttpStatus.CONFLICT);
        }
        if (!(dto.getUloga().equalsIgnoreCase("kupac") || dto.getUloga().equalsIgnoreCase("prodavac"))) {
                return new ResponseEntity<>("Uloga nije u opticaju", HttpStatus.BAD_REQUEST);
        }

        if (!dto.getLozinka().equals(dto.getPotvrdaLozinke())) {
                return new ResponseEntity<>("Nepodudaranje sifre", HttpStatus.BAD_REQUEST);

        }
        if (dto.getUloga().equalsIgnoreCase("kupac")) {
                korisnikService.createKupac(dto);
        }

        if (dto.getUloga().equalsIgnoreCase("prodavac")) {
                korisnikService.createProdavac(dto);
        }


            return new ResponseEntity<>("Uspesna registracija!", HttpStatus.OK);
        }

    @PostMapping("api/login")
    public ResponseEntity<?> login (@RequestBody LoginDto loginDto,HttpSession session) {
        if(loginDto.getKorisnickoIme().isEmpty() || loginDto.getLozinka().isEmpty()) {
            return new ResponseEntity<>("Pogresno uneti podaci.",HttpStatus.BAD_REQUEST);
        }

        Korisnik ulogovan = korisnikService.login(loginDto.getKorisnickoIme(),loginDto.getLozinka());
        if(ulogovan == null) {
            return new ResponseEntity<>("Ne postoji korisnik!",HttpStatus.NOT_FOUND);
        }

        session.setAttribute("korisnik", ulogovan);

        return ResponseEntity.ok("Uspesno ulogovan!");
    }

    @PostMapping("api/logout")
    public ResponseEntity<String> logout (HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan!", HttpStatus.FORBIDDEN);
        }
        session.invalidate();
        return new ResponseEntity<>("Uspesno izlogovan!", HttpStatus.OK);
    }

    @PutMapping("/api/ulogovan-korisnik/azuriraj")
    public ResponseEntity<UpdateDto> azuriraj (@RequestBody UpdateDto updateDto, HttpSession session ) {


        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");
        if(ulogovan == null) {
            return new ResponseEntity("Korisnik nije prijavljen!", HttpStatus.NOT_FOUND);
        }

        if(ulogovan.getUloga() != Uloga.KUPAC && ulogovan.getUloga() != Uloga.PRODAVAC) {
            return new ResponseEntity("Korisnik nije kupac ni prodavac.", HttpStatus.FORBIDDEN);
        }


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

        korisnikService.saveKorisnik(ulogovan);
        return new ResponseEntity("Uspesno azuriranje podataka!", HttpStatus.OK);
    }

    @GetMapping("/api/korisnik/{id}")
    public ResponseEntity<Korisnik> pregledProfila(@PathVariable(name="id") Long id) {
        Korisnik korisnik = korisnikService.pronadjiPoId(id);

        if(korisnik.getUloga().equals(Uloga.ADMIN)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(korisnik);

//        if(korisnik.getUloga().equals(Uloga.KUPAC)) {
//            Kupac kupac = kupacService.findOne(id);
//            return ResponseEntity.ok(kupac);
//        }
//        else {
//            Prodavac prodavac = prodavacService.findOne(id);
//            return ResponseEntity.ok(prodavac);
//        }

    }

}



