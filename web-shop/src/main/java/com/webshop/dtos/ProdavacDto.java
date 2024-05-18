package com.webshop.dtos;

public class ProdavacDto {

    private double prosecnaOcena;

    public ProdavacDto(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }
}
