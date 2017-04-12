package com.greip.core.dto;

/**
 * Created by apple on 31/03/17.
 */
public class GeClaveValorCorreoDto {
    private String clave;
    private String valor;

    public GeClaveValorCorreoDto(String clave, String valor) {
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
