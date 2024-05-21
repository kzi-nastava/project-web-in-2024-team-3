package com.webshop.dtos;

import com.webshop.model.Recenzija;

import java.io.Serializable;
import java.time.LocalDate;

public class RecenzijaProdavcaDto implements Serializable {
    private final double ocena;
    private final String komentar;
    private final LocalDate datumRecenzije;

    public RecenzijaProdavcaDto(Integer ocena, String komentar, LocalDate datumRecenzije) {
        this.ocena = ocena;
        this.komentar = komentar;
        this.datumRecenzije = datumRecenzije;
    }

    public RecenzijaProdavcaDto(Recenzija recenzija) {
        this.ocena = recenzija.getOcena();
        this.komentar = recenzija.getKomentar();
        this.datumRecenzije = recenzija.getDatum();
    }

    public double getOcena() {
        return ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public LocalDate getDatumRecenzije() {
        return datumRecenzije;
    }
}
