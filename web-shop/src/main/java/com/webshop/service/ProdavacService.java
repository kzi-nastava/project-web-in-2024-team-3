package com.webshop.service;

import com.webshop.model.Kupac;
import com.webshop.model.Prodavac;
import com.webshop.repository.ProdavacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdavacService {

    @Autowired
    ProdavacRepository prodavacRepository;

    public Prodavac findOne(Long id) {
        Optional<Prodavac> foundProdavac = prodavacRepository.findById(id);
        if(foundProdavac.isPresent()) {
            return foundProdavac.get();
        }

        return null;
    }
}
