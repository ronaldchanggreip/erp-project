package com.greip.core.dto;

import java.io.Serializable;

/**
 * Created by HvivesO on 05/01/2017.
 */
public class FiltroOrderDto implements Serializable{

    private String campo;
    private String direccion;

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
