package com.webshop.dtos;

public class KupacDto {

    private double prosecnaOcena;

    public KupacDto(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }
}
