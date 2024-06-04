package com.webshop.service;

import com.webshop.model.Kupac;
import com.webshop.model.Ponuda;
import com.webshop.model.Proizvod;
import com.webshop.repository.PonudaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PonudaService {

    @Autowired
    PonudaRepository ponudaRepository;

    public Ponuda pronadjiPoKupcu(Kupac kupac) {
        Ponuda ponuda = ponudaRepository.findByKupac(kupac);

        if(ponuda == null) {
            return null;
        }

        return ponuda;

    }

    public Ponuda savePonuda(Ponuda ponuda) {
        return ponudaRepository.save(ponuda);
    }

    public Ponuda findTopByProizvodOrderByCenaDesc(Proizvod proizvod) {
        return ponudaRepository.findTopByProizvodOrderByCenaDesc(proizvod);
    }

    public Ponuda findTopByProizvod(Proizvod proizvod) {
        return ponudaRepository.findTopByProizvod(proizvod);
    }

}
