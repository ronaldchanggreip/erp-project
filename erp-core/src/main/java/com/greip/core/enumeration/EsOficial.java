package com.greip.core.enumeration;

/**
 * Created by MDP_CHANGR on 08/09/2016.
 */
public enum EsOficial {
    T("T", "Verdadero"),
    F("F", "Verdadero");

    private String codigo;
    private String nombre;

    private EsOficial(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
}
