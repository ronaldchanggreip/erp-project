/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.greip.core.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author mdp_changr
 */
@Entity
@Table(name = "SE_OPCION_ROL")
public class SeOpcionRolDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_OPCION_ROL")
    @TableGenerator(name = "SEQ_OPCION_ROL", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "SE_OPCION_ROL", allocationSize = 1)
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "opcion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SeOpcionDto opcionDto;
    @JoinColumn(name = "rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SeRolDto rolDto;

    public SeOpcionRolDto() {
    }

    public SeOpcionRolDto(BigInteger id) {
        this.id = id;
    }

    public SeOpcionRolDto(BigInteger id, Boolean estado) {
        this.id = id;
        this.estado = estado;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public SeOpcionDto getOpcionDto() {
        return opcionDto;
    }

    public void setOpcionDto(SeOpcionDto opcionDto) {
        this.opcionDto = opcionDto;
    }

    public SeRolDto getRolDto() {
        return rolDto;
    }

    public void setRolDto(SeRolDto rolDto) {
        this.rolDto = rolDto;
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
        if (!(object instanceof SeOpcionRolDto)) {
            return false;
        }
        SeOpcionRolDto other = (SeOpcionRolDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.SeOpcionRolDto[ id=" + id + " ]";
    }

}
