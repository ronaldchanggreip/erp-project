package com.greip.core.dto;

import java.io.Serializable;

/**
 * Created by HvivesO on 05/01/2017.
 */
public class FiltroDetaDto implements Serializable{

    private String campo;
    private String operador;
    private String valor;//Si es like el usuario debe enviar el valor con los porcentajes segun su necesidad
    private String tipoDato;

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }
}
