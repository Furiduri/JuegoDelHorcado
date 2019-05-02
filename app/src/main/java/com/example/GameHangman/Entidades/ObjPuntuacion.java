package com.example.GameHangman.Entidades;

public class ObjPuntuacion {
    private String ID;
    private String Name;
    private String Palabra;
    private String Nivel;
    private String Life;
    private String Time;
    private String Puntuacion;

    public ObjPuntuacion(String ID, String name, String palabra, String level, String life, String time, String puntuacion) {
        this.ID = ID;
        Name = name;
        Palabra = palabra;
        Nivel = level;
        Life = life;
        Time = time;
        Puntuacion = puntuacion;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getPalabra() {
        return Palabra;
    }

    public String getNivel() {
        return Nivel;
    }

    public String getLife() {
        return Life;
    }

    public String getTime() {
        return Time;
    }

    public String getPuntuacion() {
        return Puntuacion;
    }
}
