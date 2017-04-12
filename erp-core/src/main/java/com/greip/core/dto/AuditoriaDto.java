package com.greip.core.dto;

import java.util.Date;

/**
 * Created by HvivesO on 10/01/2017.
 */
public class AuditoriaDto {

    private Date fecha;

    private String terminal;

    private SeUsuarioDto usuarioDto;

    private Date fechaCreacion;

    private String terminalCreacion;

    private SeUsuarioDto usuarioCreacionDto;

    private String comentario;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public SeUsuarioDto getUsuarioDto() {
        return usuarioDto;
    }

    public void setUsuarioDto(SeUsuarioDto usuarioDto) {
        this.usuarioDto = usuarioDto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTerminalCreacion() {
        return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
        this.terminalCreacion = terminalCreacion;
    }

    public SeUsuarioDto getUsuarioCreacionDto() {
        return usuarioCreacionDto;
    }

    public void setUsuarioCreacionDto(SeUsuarioDto usuarioCreacionDto) {
        this.usuarioCreacionDto = usuarioCreacionDto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
