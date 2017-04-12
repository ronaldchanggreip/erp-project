/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.greip.core.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author mdp_changr
 */
@Entity
@Table(name = "SE_ROL")
public class SeRolDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_ROL")
    @TableGenerator(name = "SEQ_ROL", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "SE_ROL", allocationSize = 1)
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "ind_administrador")
    private Boolean indAdministrador;
    @Column(name = "ind_sys")
    private Boolean indSys;
    @Column(name = "estado")
    private String estado;

    //Campos de auditoria
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "terminal")
    private String terminal;
    @Column(name = "usuario")
    private BigInteger usuario;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "terminal_creacion")
    private String terminalCreacion;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "usuario_creacion")
    private BigInteger usuarioCreacion;
    @Column(name = "ELIMINADO")
    private Boolean eliminado = Boolean.FALSE;

    @Transient
    private String[] opciones;
    @Transient
    private List<TreeWebDto> treeWebSelected;

    public SeRolDto() {
    }

    public SeRolDto(BigInteger id) {
        this.id = id;
    }

    public SeRolDto(BigInteger id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getIndAdministrador() {
        return indAdministrador;
    }

    public void setIndAdministrador(Boolean indAdministrador) {
        this.indAdministrador = indAdministrador;
    }

    public Boolean getIndSys() {
        return indSys;
    }

    public void setIndSys(Boolean indSys) {
        this.indSys = indSys;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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

    public BigInteger getUsuario() {
        return usuario;
    }

    public void setUsuario(BigInteger usuario) {
        this.usuario = usuario;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigInteger getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(BigInteger usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
    }

    public List<TreeWebDto> getTreeWebSelected() {
        return treeWebSelected;
    }

    public void setTreeWebSelected(List<TreeWebDto> treeWebSelected) {
        this.treeWebSelected = treeWebSelected;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeRolDto)) {
            return false;
        }
        SeRolDto other = (SeRolDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.SeRolDto[ id=" + id + " ]";
    }

}
