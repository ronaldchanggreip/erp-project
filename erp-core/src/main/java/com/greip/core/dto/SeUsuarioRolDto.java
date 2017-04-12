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

/**
 * @author mdp_changr
 */
@Entity
@Table(name = "SE_USUARIO_ROL")
public class SeUsuarioRolDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_USUARIO_ROL")
    @TableGenerator(name = "SEQ_USUARIO_ROL", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "SE_USUARIO_ROL", allocationSize = 1)
    private BigInteger id;
    @Column(name = "fec_vigencia")
    @Temporal(TemporalType.DATE)
    private Date fecVigencia;
    @Basic(optional = false)
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SeRolDto rolDto;
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SeUsuarioDto usuarioDto;

    public SeUsuarioRolDto() {
    }

    public SeUsuarioRolDto(BigInteger id) {
        this.id = id;
    }

    public SeUsuarioRolDto(BigInteger id, Boolean estado) {
        this.id = id;
        this.estado = estado;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getFecVigencia() {
        return fecVigencia;
    }

    public void setFecVigencia(Date fecVigencia) {
        this.fecVigencia = fecVigencia;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public SeRolDto getRolDto() {
        return rolDto;
    }

    public void setRolDto(SeRolDto rolDto) {
        this.rolDto = rolDto;
    }

    public SeUsuarioDto getUsuarioDto() {
        return usuarioDto;
    }

    public void setUsuarioDto(SeUsuarioDto usuarioDto) {
        this.usuarioDto = usuarioDto;
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
        if (!(object instanceof SeUsuarioRolDto)) {
            return false;
        }
        SeUsuarioRolDto other = (SeUsuarioRolDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.SeUsuarioRolDto[ id=" + id + " ]";
    }

}
