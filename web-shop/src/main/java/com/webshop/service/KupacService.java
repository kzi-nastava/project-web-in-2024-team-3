package com.webshop.service;

import com.webshop.model.Kupac;
import com.webshop.model.Proizvod;
import com.webshop.repository.KupacRepository;
import com.webshop.repository.ProizvodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KupacService {

    @Autowired
    KupacRepository kupacRepository;
    @Autowired
    private ProizvodRepository proizvodRepository;

    public Kupac findOne(Long id) {
        Optional<Kupac> foundKupac = kupacRepository.findById(id);
        if(foundKupac.isPresent()) {
            return foundKupac.get();
        }

        return null;
    }


}
