package com.webshop.service;

import com.webshop.model.Kategorija;
import com.webshop.model.Proizvod;
import com.webshop.model.TIP;
import com.webshop.repository.ProizvodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProizvodService {

    @Autowired
    private ProizvodRepository proizvodRepository;

    public List<Proizvod> findAll() { return proizvodRepository.findAll();}

    public List<Proizvod> pronadjiPoNazivuIOpisu(String upit) {
        List<Proizvod> proizvodi = proizvodRepository.findByNazivContainingOrOpisContainingIgnoreCase(upit,upit);
        if(proizvodi.isEmpty())
            return null;
        else
            return proizvodi;
    }

    public List<Proizvod> pronadjiPoCeni(double min,double max) {
        List<Proizvod> proizvodi = proizvodRepository.findByCenaBetween(min,max);

        if(proizvodi.isEmpty()) {
            return null;
        }

        return proizvodi;
    }

    public List<Proizvod> pronadjiPoKategoriji(Kategorija kategorija) {
        List<Proizvod> proizvodi = proizvodRepository.findByKategorija(kategorija);

        if(proizvodi.isEmpty()) {
            return null;
        }

        return proizvodi;
    }

    public List<Proizvod> pronadjiPoTipu(TIP tip) {
        List<Proizvod> proizvodi = proizvodRepository.findByTipProdaje(tip);

        if(proizvodi.isEmpty()) {
            return null;
        }

        return proizvodi;
    }
}
