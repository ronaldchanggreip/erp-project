package com.greip.core.dto;

import java.io.File;

/**
 * Created by esvr on 29/03/17.
 */
public class GeCorreoDto {

    private String[] to;
    private String[] cc;
    private String[] cco;

    private String asunto;
    private String cuerpo;
    private String mensajeAdjunto;
    private File[] adjuntos;

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getCco() {
        return cco;
    }

    public void setCco(String[] cco) {
        this.cco = cco;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getMensajeAdjunto() {
        return mensajeAdjunto;
    }

    public void setMensajeAdjunto(String mensajeAdjunto) {
        this.mensajeAdjunto = mensajeAdjunto;
    }

    public File[] getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(File[] adjuntos) {
        this.adjuntos = adjuntos;
    }
}
