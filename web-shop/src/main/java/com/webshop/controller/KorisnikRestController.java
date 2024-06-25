package com.webshop.controller;

import com.webshop.dtos.*;
import com.webshop.model.*;
import com.webshop.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


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

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Uspesno ulogovan!");
        response.put("korisnik", ulogovan);

        return ResponseEntity.ok(response);
    }

    @PostMapping("api/logout")
    public ResponseEntity<?> logout (HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            System.out.println("Korisnik nije ulogovan!");
            System.out.println("Sesija ID: " + session.getId());
            return new ResponseEntity<>("Korisnik nije ulogovan!", HttpStatus.FORBIDDEN);
        }
        System.out.println("Korisnik pre logout-a: " + korisnik.getIme());
        System.out.println("Sesija ID: " + session.getId());
        session.invalidate();
        System.out.println("Sesija invalidirana");
        return new ResponseEntity<>("Uspesno izlogovan!", HttpStatus.OK);
    }

    @GetMapping("/ulogovan")
    public ResponseEntity<?> ulogovanKorisnik(HttpSession session) {
        Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (ulogovaniKorisnik == null) {
            return new ResponseEntity<>("Nijedan korisnik nije prijavljen!", HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(ulogovaniKorisnik);
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


        if (updateDto.getIme() != null && !updateDto.getIme().isEmpty()) {
            ulogovan.setIme(updateDto.getIme());
        }

        if (updateDto.getPrezime() != null && !updateDto.getPrezime().isEmpty()) {
            ulogovan.setPrezime(updateDto.getPrezime());
        }

        if (updateDto.getBrojTelefona() != null && !updateDto.getBrojTelefona().isEmpty()) {
            ulogovan.setBrojTelefona(updateDto.getBrojTelefona());
        }


        if (updateDto.getDatumRodjenja() != null) {
            ulogovan.setDatumRodjenja(updateDto.getDatumRodjenja());
        }

        if (updateDto.getProfilnaSlika() != null && !updateDto.getProfilnaSlika().isEmpty()) {
            String filePath = saveImage(updateDto.getProfilnaSlika(), ulogovan.getKorisnickoIme());
            ulogovan.setProfilnaSlika(filePath);
        }

        if (updateDto.getOpis() != null && !updateDto.getOpis().isEmpty()) {
            ulogovan.setOpis(updateDto.getOpis());
        }

        if(Objects.equals(updateDto.getStaraLozinka(), ulogovan.getLozinka())) {

            if (updateDto.getNovaLozinka() != null && !updateDto.getNovaLozinka().isEmpty()) {
                ulogovan.setLozinka(updateDto.getNovaLozinka());
            }

            if (updateDto.getKorisnickoIme() != null && !updateDto.getKorisnickoIme().isEmpty()) {
                ulogovan.setKorisnickoIme(updateDto.getKorisnickoIme());
            }

            if (updateDto.getEmail() != null && !updateDto.getEmail().isEmpty()) {
                ulogovan.setEmail(updateDto.getEmail());
            }

        } else {
            return new ResponseEntity("Pogresna lozinka", HttpStatus.BAD_REQUEST);
        }

        korisnikService.saveKorisnik(ulogovan);
        return new ResponseEntity("Uspesno azuriranje podataka!", HttpStatus.OK);
    }

    private String saveImage(String base64Image, String username) {
        try {
            String[] parts = base64Image.split(",");
            String imageString = parts[1];
            byte[] imageBytes = Base64.getDecoder().decode(imageString);
            String userHome = System.getProperty("user.home");
            String directoryPath = Paths.get(userHome, "Downloads").toString();
            String filePath = directoryPath + "/" + username + ".jpg";


            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(imageBytes);
            }
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Error while saving image", e);
        }
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

    private static final Logger logger = Logger.getLogger(KorisnikRestController.class.getName());

    @GetMapping("/kupac/{id}")
    public ResponseEntity<KupacDto> kupacProfil(@PathVariable(name="id") Long kupacId) {
        logger.info("Primljen zahtev za kupca sa ID: " + kupacId);
        Korisnik korisnik = korisnikService.pronadjiKupcaPoId(kupacId);

        if (korisnik == null || !korisnik.getUloga().equals(Uloga.KUPAC)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Kupac kupac = (Kupac) korisnik;
        KupacDto kupacDto = new KupacDto(kupac, kupac.getProsecnaOcena(), kupac.getKupljeniProizvodi(), kupac.getKupacRecenzije());


        return ResponseEntity.ok(kupacDto);
    }

    @GetMapping("/kupac/{id}/proizvodi")
    public ResponseEntity<Set<Proizvod>> getKupljeniProizvodi(@PathVariable(name="id") Long kupacId) {
        logger.info("Primljen zahtev za proizvode kupca sa ID: " + kupacId);
        try {
            Korisnik korisnik = korisnikService.pronadjiKupcaPoId(kupacId);
            if (korisnik == null || !(korisnik instanceof Kupac)) {
                logger.warning("Kupac sa ID: " + kupacId + " nije pronađen ili nije tipa Kupac");
                return ResponseEntity.notFound().build();
            }

            Kupac kupac = (Kupac) korisnik;
            Set<Proizvod> proizvodi = kupac.getKupljeniProizvodi();
            logger.info("Proizvodi za kupca sa ID: " + kupacId + " su uspešno preuzeti");
            return ResponseEntity.ok(proizvodi);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Greška pri preuzimanju proizvoda za kupca sa ID: " + kupacId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/prodavac/{id}")
    public ResponseEntity<ProdavacDto> prodavacProfil(@PathVariable(name="id") Long prodavacId) {
        logger.info("Primljen zahtev za prodavca sa ID: " + prodavacId);
        Korisnik korisnik = korisnikService.pronadjiProdavcaPoId(prodavacId);

        if (korisnik == null || !korisnik.getUloga().equals(Uloga.PRODAVAC)) {
            logger.warning("Prodavac sa ID: " + prodavacId + " nije pronađen ili nije tipa Prodavac");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Prodavac prodavac = (Prodavac) korisnik;
            ProdavacDto prodavacDto = new ProdavacDto(prodavac, prodavac.getProsecnaOcena(), prodavac.getProizvodiNaProdaju(), prodavac.getProdavacRecenzije());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(prodavacDto);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Greška pri preuzimanju podataka za prodavca sa ID: " + prodavacId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/prodavac/{id}/proizvodi")
    public ResponseEntity<Set<Proizvod>> getProdavacProizvodi(@PathVariable(name="id") Long prodavacId) {
        logger.info("Primljen zahtev za proizvode prodavca sa ID: " + prodavacId);
        try {
            Korisnik korisnik = korisnikService.pronadjiProdavcaPoId(prodavacId);
            if (korisnik == null || !(korisnik instanceof Prodavac)) {
                logger.warning("Prodavac sa ID: " + prodavacId + " nije pronađen ili nije tipa Prodavac");
                return ResponseEntity.notFound().build();
            }

            Prodavac prodavac = (Prodavac) korisnik;
            Set<Proizvod> proizvodi = prodavac.getProizvodiNaProdaju();
            logger.info("Proizvodi za prodavca sa ID: " + prodavacId + " su uspešno preuzeti");
            return ResponseEntity.ok(proizvodi);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Greška pri preuzimanju proizvoda za prodavca sa ID: " + prodavacId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    @GetMapping("/sviProfili")
    public ResponseEntity<?> getAllKupciAndProdavci(HttpSession session) {
        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");

        if (ulogovan == null) {
            return new ResponseEntity<>("Korisnik nije prijavljen!", HttpStatus.UNAUTHORIZED);
        }

        if (ulogovan.getUloga() != Uloga.KUPAC && ulogovan.getUloga() != Uloga.PRODAVAC) {
            return new ResponseEntity<>("Korisnik nije ovlašćen da pregleda profile!", HttpStatus.FORBIDDEN);
        }

        List<Korisnik> korisnici = korisnikService.pronadjiSve().stream()
                .filter(k -> k.getUloga() == Uloga.KUPAC || k.getUloga() == Uloga.PRODAVAC)
                .collect(Collectors.toList());

        return ResponseEntity.ok(korisnici);
    }


}



