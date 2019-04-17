package com.example.GameHangman.Entidades;

public class ObjPalabra {
    private Integer ID;
    private String Palabra;
    private Integer Nivel;

    public ObjPalabra(Integer ID, String palabra, Integer nivel) {
        this.ID = ID;
        Palabra = palabra;
        Nivel = nivel;
    }

    public Integer getID() {
        return ID;
    }

    public String getPalabra() {
        return Palabra;
    }

    public Integer getNivel() {
        return Nivel;
    }
}
