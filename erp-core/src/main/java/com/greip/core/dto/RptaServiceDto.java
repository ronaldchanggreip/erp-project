package com.greip.core.dto;

/**
 * Created by esvr on 29/03/17.
 */
public class RptaServiceDto {

    private boolean ok;
    private String mensaje;

    public RptaServiceDto(boolean ok, String mensaje){
        this.ok = ok;
        this.mensaje = mensaje;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
