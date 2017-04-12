package com.greip.core.enumeration;

public enum Continente {
    AS("Asia", 1),
    EU("Europa", 2),
    NA("Norte America", 3),
    AF("Africa", 4),
    OC("Oceania", 5),
    SA("Sudamerica", 1);

    private String nombre;
    private int codigo;


    private Continente(String nombre, int codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCodigo() {
        return codigo;
    }
}
