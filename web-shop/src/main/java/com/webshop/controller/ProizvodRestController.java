package com.webshop.controller;

import com.webshop.dtos.PostavljanjeProdajeDto;
import com.webshop.dtos.ProizvodDto;
import com.webshop.model.*;
import com.webshop.repository.KategorijaRepository;
import com.webshop.repository.KorisnikRepository;
import com.webshop.repository.PonudaRepository;
import com.webshop.repository.ProizvodRepository;
import com.webshop.service.*;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


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
    @Autowired
    private EmailService emailService;
    @Autowired
    private KategorijaService kategorijaService;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private ProizvodRepository proizvodRepository;
    @Autowired
    private KategorijaRepository kategorijaRepository;
    @Autowired
    private PonudaRepository ponudaRepository;


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

        for (Proizvod proizvod : proizvodi) {
            ProizvodDto dto = new ProizvodDto(proizvod);
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);

    }

    @GetMapping(value = "/api/proizvodi/filtrirajProizvode")
    public ResponseEntity<List<ProizvodDto>> getProizvodiByFilter(@RequestParam(required = false) Double min, @RequestParam(required = false) Double max, @RequestParam(required = false) TIP tipProdaje, @RequestParam(required = false) String kategorija) {

        List<Proizvod> proizvodi = proizvodService.filtrirajProizvod(min, max, tipProdaje, kategorija);
        /*List<ProizvodDto> dtos = new ArrayList<>();

        for (Proizvod proizvod : proizvodi) {
            ProizvodDto dto = new ProizvodDto(proizvod);
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);*/
        return getListResponseEntity(proizvodi);



    }
    private ResponseEntity<List<ProizvodDto>> getListResponseEntity(List<Proizvod> proizvodList) {
        List<ProizvodDto> proizvodDtos = new ArrayList<>();

        if (proizvodList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (Proizvod proizvod : proizvodList) {
            proizvodDtos.add(new ProizvodDto(proizvod));
        }
        return new ResponseEntity<>(proizvodDtos, HttpStatus.OK);
    }

    @GetMapping("/api/kategorije/{id}/proizvodi")
    public ResponseEntity<List<ProizvodDto>> pretragaPoKategoriji(@PathVariable(name = "id") Long id) {
        List<Proizvod> proizvodi = proizvodService.pronadjiPoKategoriji(id);

        List<ProizvodDto> dtos = new ArrayList<>();

        for (Proizvod proizvod : proizvodi) {
            ProizvodDto dto = new ProizvodDto(proizvod);
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/api/proizvod-fiskna/{idProizvod}")
    public ResponseEntity<?> kupovinaProizovdaFiksna(@PathVariable Long idProizvod, HttpSession session) {

        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");
        if (ulogovan == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan!", HttpStatus.BAD_REQUEST);
        }

        if (ulogovan.getUloga() != Uloga.KUPAC) {
            return new ResponseEntity<>("Korisnik nije kupac!", HttpStatus.FORBIDDEN);
        }

        Optional<Kupac> ulogovanKupacOptional = Optional.ofNullable(kupacService.findOne(ulogovan.getId()));
        Optional<Proizvod> proizvodOptional = Optional.ofNullable(proizvodService.pronadjiPoId(idProizvod));

        if (proizvodOptional.isEmpty()) {

            return new ResponseEntity<>("Ne postoji proizvod!", HttpStatus.NOT_FOUND);
        }

        Proizvod proizvod = proizvodOptional.get();

        if (proizvod.jeProdat()) {
            return new ResponseEntity<>("Proizvod je vec prodat!", HttpStatus.BAD_REQUEST);
        }

        Kupac ulogovanKupac = ulogovanKupacOptional.get();
        Optional<Prodavac> prodavacOptional = Optional.ofNullable(prodavacService.findOne(proizvod.getProdavac().getId()));

        if (proizvod.getTipProdaje() != TIP.FIKSNACENA) {
            return new ResponseEntity<>("Proizvod nema fiksnu cenu!", HttpStatus.BAD_REQUEST);
        }

        if (proizvod.getProdavac() == null) {
            return new ResponseEntity<>("Proizvod nema prodavca!", HttpStatus.NOT_FOUND);
        }

        Prodavac prodavac = prodavacOptional.get();
        prodavac.ukloniProizvodNaProdaju(proizvod);
        proizvod.setProdat(true);
        ulogovanKupac.dodajKupljeniProizvod(proizvod);
        korisnikService.saveKorisnik(ulogovanKupac);
        korisnikService.saveKorisnik(prodavac);
        proizvodService.saveProizvod(proizvod);


        String kupacEmail = ulogovanKupac.getEmail();
        String prodavacEmail = prodavac.getEmail();
        String imeProizvoda = proizvod.getNaziv();

//        emailService.sendEmail(kupacEmail,"Potvrda kupovine","Uspesno ste kupili proizvod" + imeProizvoda);
//        emailService.sendEmail(prodavacEmail,"Prodaja proizvoda", "Prodali ste proizvod" + imeProizvoda);

        return new ResponseEntity<>("Proizvod je prodat!", HttpStatus.OK);

    }

    @PostMapping("/api/proizvod-aukcija/{idProizvod}")
    public ResponseEntity<?> kupovinaProizvodaAukcija(@PathVariable Long idProizvod, @RequestParam double ponuda, HttpSession session) {
        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");
        if (ulogovan == null) {
            return new ResponseEntity<>("Korisnik nije ulogovan!", HttpStatus.BAD_REQUEST);
        }

        if (ulogovan.getUloga() != Uloga.KUPAC) {
            return new ResponseEntity<>("Korisnik nije kupac!", HttpStatus.FORBIDDEN);
        }

        Optional<Kupac> ulogovanKupacOptional = Optional.ofNullable(kupacService.findOne(ulogovan.getId()));
        Optional<Proizvod> proizvodOptional = Optional.ofNullable(proizvodService.pronadjiPoId(idProizvod));

        if (proizvodOptional.isEmpty()) {

            return new ResponseEntity<>("Ne postoji proizvod!", HttpStatus.NOT_FOUND);
        }

        Proizvod proizvod = proizvodOptional.get();

        if (proizvod.jeProdat()) {
            return new ResponseEntity<>("Proizvod je prodat!", HttpStatus.BAD_REQUEST);
        }

        if (proizvod.getProdavac() == null) {
            return new ResponseEntity<>("Proizvod nema prodavca!", HttpStatus.NOT_FOUND);
        }

        if (proizvod.getTipProdaje() != TIP.AUKCIJA) {
            return new ResponseEntity<>("Proizvod nije na aukciji!", HttpStatus.BAD_REQUEST);
        }

        if (ponuda < proizvod.getCena()) {
            return new ResponseEntity<>("Ponuda je preniska!", HttpStatus.BAD_REQUEST);
        }


        Ponuda najvecaPonuda = ponudaService.findTopByProizvodOrderByCenaDesc(proizvod);
        if (najvecaPonuda != null && ponuda <= najvecaPonuda.getCena()) {

            return new ResponseEntity<>("Ponuda je preniska!", HttpStatus.BAD_REQUEST);
        }

        Kupac ulogovanKupac = ulogovanKupacOptional.get();

        Ponuda novaPonuda = new Ponuda(ponuda, proizvod, ulogovanKupac);
        proizvod.dodajPonudu(novaPonuda);
        ponudaService.savePonuda(novaPonuda);

        String kupacEmail = ulogovanKupac.getEmail();
        String prodavacEmail = proizvod.getProdavac().getEmail();

//        emailService.sendEmail(prodavacEmail,"Nova ponuda za proizvod","Imate novu ponudu za proizvod" + proizvod.getNaziv() + " od strane " + kupacEmail + ".");

        return new ResponseEntity<>("Ponuda je sacuvana!", HttpStatus.OK);
    }

    @PostMapping("api/postavi-proizvod")
    public ResponseEntity<?> postaviProizvod(@RequestBody PostavljanjeProdajeDto postavljanjeProdajeDto, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (loggedKorisnik == null) {
            return new ResponseEntity<>("Morate biti ulogovani!", HttpStatus.BAD_REQUEST);
        }
        if (loggedKorisnik.getUloga() != Uloga.PRODAVAC) {
            return new ResponseEntity<>("Niste prodavac!", HttpStatus.FORBIDDEN);
        }
        Prodavac prodavac = prodavacService.findOne(loggedKorisnik.getId());
        if (prodavac == null) {
            return new ResponseEntity<>("Nije pronadjen prodavac sa tim id.", HttpStatus.BAD_REQUEST);
        }

        Proizvod proizvod = new Proizvod();
        proizvod.setProdavac(prodavac);
        proizvod.setCena(postavljanjeProdajeDto.getCena());
        proizvod.setNaziv(postavljanjeProdajeDto.getNaziv());
        proizvod.setRecenzijaOdKupca(false);
        proizvod.setRecenzijaOdProdavca(false);
        proizvod.setProdat(false);
        proizvod.setOpis(postavljanjeProdajeDto.getOpis());
        proizvod.setDatumObjavljivanja(LocalDate.now());
        proizvod.setSlika(postavljanjeProdajeDto.getSlika());
        proizvod.setPonudeZaProizvod(null);
        if (!postavljanjeProdajeDto.getTipProdaje().equals(TIP.AUKCIJA)) {
            proizvod.setTipProdaje(TIP.AUKCIJA);
        } else if (postavljanjeProdajeDto.getTipProdaje().equals(TIP.FIKSNACENA)) {
            proizvod.setTipProdaje(TIP.FIKSNACENA);
        } else {
            return new ResponseEntity<>("Wrong type of selling!", HttpStatus.BAD_REQUEST);
        }

        Kategorija kategorija = kategorijaService.getKategorija(postavljanjeProdajeDto.getKategorija().getNaziv());
        if (kategorija == null) {
            Kategorija kategorija1 = new Kategorija(postavljanjeProdajeDto.getKategorija());
            proizvod.setKategorija(kategorija1);
            kategorijaRepository.save(kategorija1);

        } else {
            proizvod.setKategorija(kategorija);
            kategorijaRepository.save(kategorija);

        }

        prodavac.dodajProizvod(proizvod);
        proizvodRepository.save(proizvod);
        korisnikRepository.save(prodavac);

        return new ResponseEntity<>("New product is successfully saved.", HttpStatus.OK);
    }

    @PutMapping("api/azuriranjeProizvoda/{id}")
    public ResponseEntity<?> azuriranjeProizvoda(@RequestBody @Nullable ProizvodDto proizvodDto, @PathVariable Long id, HttpSession session) {
        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");
        if (ulogovan == null) {
            return new ResponseEntity<>("Korisnik nije prijavljen!", HttpStatus.BAD_REQUEST);
        }
        if (ulogovan.getUloga() != Uloga.PRODAVAC) {
            return new ResponseEntity<>("Niste prijavljeni kao PRODAVAC", HttpStatus.FORBIDDEN);
        }
        Optional<Proizvod> proizvod = proizvodRepository.findById(id);
        List<Ponuda> ponudeZaProizvod = ponudaRepository.findAllByProizvod(proizvod);
        Prodavac prodavac = (Prodavac) ulogovan;
        if (proizvod.get().getProdavac().getId() != prodavac.getId()) {
            return new ResponseEntity<>("Ovaj prodavac ne prodaje trazeni proizvod", HttpStatus.NOT_FOUND);
        }
        if (proizvod.get().getTipProdaje() == TIP.AUKCIJA) {
            if (!ponudeZaProizvod.isEmpty()) {
                return new ResponseEntity<>("Nije moguce izmeniti informacije o proizvodu jer za taj proizvod postoje ponude", HttpStatus.BAD_REQUEST);
            }
        }
        if (proizvodDto.getNaziv() != null)
            proizvod.get().setNaziv(proizvodDto.getNaziv());
        if (proizvodDto.getOpis() != null)
            proizvod.get().setOpis(proizvodDto.getOpis());
        if (proizvodDto.getKategorija() != null) {
            boolean kategorija = kategorijaRepository.existsByNaziv(proizvodDto.getKategorija().getNaziv());
            if (!kategorija) {
                Kategorija novaKategorija = new Kategorija();
                novaKategorija.setNaziv(proizvodDto.getKategorija().getNaziv());
                proizvod.get().setKategorija(novaKategorija);
                kategorijaRepository.save(novaKategorija);
            } else {
                Kategorija kategorija1 = kategorijaRepository.findByNaziv(proizvodDto.getKategorija().getNaziv());
                proizvod.get().setKategorija(kategorija1);
            }
        }

        proizvod.get().setCena(proizvodDto.getCena());
        if (proizvodDto.getSlika() != null)
            proizvod.get().setSlika(proizvodDto.getSlika());
        if (proizvodDto.getTipProdaje() != null)
            proizvod.get().setTipProdaje(proizvodDto.getTipProdaje());

        proizvodRepository.save(proizvod.get());
        return new ResponseEntity<>("Proizvod je uspeno updatovan", HttpStatus.OK);
    }

    @GetMapping("/prodavac/krajaukcije/{id}")
    public ResponseEntity<?> krajAukcije(@PathVariable Long id, HttpSession session) {
        Korisnik ulogovan = (Korisnik) session.getAttribute("korisnik");
        if(ulogovan == null) {
            return new ResponseEntity<>("Korisnik nije prijavljen!", HttpStatus.BAD_REQUEST);
        }
        if(ulogovan.getUloga() != Uloga.PRODAVAC) {
            return new ResponseEntity<>("Niste prijavljeni kao PRODAVAC", HttpStatus.FORBIDDEN);
        }

        Optional<Proizvod> proizvod = proizvodRepository.findById(id);
        List<Ponuda> ponude = ponudaRepository.findAllByProizvod(proizvod);

        if(proizvod.get().jeProdat() || ponude.isEmpty()) {
            return new ResponseEntity<>("Aukcija nije aktivna ili nema ponuda", HttpStatus.BAD_REQUEST);
        }

        proizvod.get().setProdat(true);
        proizvodRepository.save(proizvod.get());

        Ponuda ponuda = ponudaRepository.findTopByProizvod(proizvod.get());


        Kupac kupac = ponuda.getKupac();
        Set<Proizvod> kupljeniProizvodi = kupac.getKupljeniProizvodi();

        Prodavac prodavac = (Prodavac) ulogovan;
        Set<Proizvod> proizvodiNaProdaju = prodavac.getProizvodiNaProdaju();

        if(proizvodiNaProdaju.contains(proizvod)) {
            kupljeniProizvodi.add(proizvod.get());
            proizvodiNaProdaju.remove(proizvod);
        }

        String prodavacEmail=prodavac.getEmail();
//        emailService.sendEmail(prodavacEmail,"Zavrsena aukcija","Proizvod je prodat "+kupac.getIme()+" po ceni od "+ponuda.getCena());
        String kupacEmail;
        for(Ponuda p:ponude){
            kupacEmail=p.getKupac().getEmail();
//            emailService.sendEmail( kupacEmail,"Zavrsena aukcija","Kupljen proizvod");
        }
        return ResponseEntity.ok("Aukcija je zavrsena!");
    }

}
