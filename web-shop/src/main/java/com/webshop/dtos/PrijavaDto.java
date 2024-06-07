package com.webshop.dtos;

import com.webshop.model.Korisnik;

import java.io.Serializable;

public class PrijavaDto implements Serializable {


    private String razlogZaPrijavu;

    public PrijavaDto() {
    }

    public PrijavaDto(String razlogZaPrijavu) {
        this.razlogZaPrijavu = razlogZaPrijavu;
    }

    public String getRazlogZaPrijavu() {
        return razlogZaPrijavu;
    }

    public void setRazlogZaPrijavu(String razlogZaPrijavu) {
        this.razlogZaPrijavu = razlogZaPrijavu;
    }


}