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
@Table(name = "GE_ENTIDAD")
public class GeEntidadDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "correlativo")
    private BigInteger correlativo;
    @Basic(optional = false)
    @Column(name = "modulo")
    private String modulo;

    public GeEntidadDto() {
    }

    public GeEntidadDto(BigInteger id) {
        this.id = id;
    }

    public GeEntidadDto(BigInteger id, String nombre, BigInteger correlativo, String modulo) {
        this.id = id;
        this.nombre = nombre;
        this.correlativo = correlativo;
        this.modulo = modulo;
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

    public BigInteger getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(BigInteger correlativo) {
        this.correlativo = correlativo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
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
        if (!(object instanceof GeEntidadDto)) {
            return false;
        }
        GeEntidadDto other = (GeEntidadDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.GeEntidadDto[ id=" + id + " ]";
    }

}
