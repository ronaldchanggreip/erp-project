package com.greip.core.dto;

import java.math.BigInteger;

/**
 * Created by ronaldchang on 19/11/16.
 */
public class GeMensajeControllerDto {

    private String httpStatus;
    private String mensaje;


    private int codigoHttp; //Codigo http del Mensaje
    private String codigoGrupoHttp; //Codigo del Grupo del Mensaje
    private String nombreGrupoHttp; //Grupo al que pertenece el mensaje
    private String resumenHttp; //Resumen del Mensaje (Titulo)
    private String mensajeSistemaHttp; //Mensaje a mostrar para Sistemas
    private String mensajeUsuario; //Mensaje a mostrar al usuario
    private String detalle; //Algun detalle adicional del mensaje

    private Object respuesta; //En este objeto va la respuesta o el objeto que se desea mandar

    public GeMensajeControllerDto(String httpStatus, String mensaje, String detalle) {
        this.httpStatus = httpStatus;
        this.mensaje = mensaje;
        this.detalle = detalle;
    }


    public GeMensajeControllerDto(int codigoHttp, String codigoGrupoHttp, String nombreGrupoHttp, String resumenHttp, String mensajeSistemaHttp, String mensajeUsuario) {
        this.codigoHttp = codigoHttp;
        this.codigoGrupoHttp = codigoGrupoHttp;
        this.nombreGrupoHttp = nombreGrupoHttp;
        this.resumenHttp = resumenHttp;
        this.mensajeSistemaHttp = mensajeSistemaHttp;
        this.mensajeUsuario = mensajeUsuario;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigoHttp() {
        return codigoHttp;
    }

    public void setCodigoHttp(int codigoHttp) {
        this.codigoHttp = codigoHttp;
    }

    public String getCodigoGrupoHttp() {
        return codigoGrupoHttp;
    }

    public void setCodigoGrupoHttp(String codigoGrupoHttp) {
        this.codigoGrupoHttp = codigoGrupoHttp;
    }

    public String getNombreGrupoHttp() {
        return nombreGrupoHttp;
    }

    public void setNombreGrupoHttp(String nombreGrupoHttp) {
        this.nombreGrupoHttp = nombreGrupoHttp;
    }

    public String getResumenHttp() {
        return resumenHttp;
    }

    public void setResumenHttp(String resumenHttp) {
        this.resumenHttp = resumenHttp;
    }

    public String getMensajeSistemaHttp() {
        return mensajeSistemaHttp;
    }

    public void setMensajeSistemaHttp(String mensajeSistemaHttp) {
        this.mensajeSistemaHttp = mensajeSistemaHttp;
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Object getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Object respuesta) {
        this.respuesta = respuesta;
    }
}
