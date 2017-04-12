package com.greip.core.dto;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public class GeRedisDto {

    private String clave;
    private String valor;

    public GeRedisDto(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
