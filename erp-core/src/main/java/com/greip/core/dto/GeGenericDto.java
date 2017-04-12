package com.greip.core.dto;

import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrador on 07/12/2016.
 */
@MappedSuperclass
public abstract class GeGenericDto {

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "terminal")
    private String terminal;

    @JsonManagedReference
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne
    private SeUsuarioDto usuarioDto;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "terminal_creacion")
    private String terminalCreacion;

    @JoinColumn(name = "usuario_creacion", referencedColumnName = "id")
    @ManyToOne
    private SeUsuarioDto usuarioCreacionDto;

    @Column(name = "ELIMINADO")
    private Boolean eliminado = Boolean.FALSE;

    @Column(name = "comentario")
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
