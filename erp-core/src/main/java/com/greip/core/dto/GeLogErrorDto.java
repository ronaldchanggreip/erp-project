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
@Table(name = "GE_LOG_ERROR")
public class GeLogErrorDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "proceso")
    private String proceso;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_GE_LOG_ERROR")
    @TableGenerator(name = "SEQ_GE_LOG_ERROR", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_LOG_ERROR", allocationSize = 1)
    private BigInteger id;

    public GeLogErrorDto() {
    }

    public GeLogErrorDto(BigInteger id) {
        this.id = id;
    }

    public GeLogErrorDto(BigInteger id, String proceso, Date fecha, String tipo) {
        this.id = id;
        this.proceso = proceso;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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
        if (!(object instanceof GeLogErrorDto)) {
            return false;
        }
        GeLogErrorDto other = (GeLogErrorDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.GeLogErrorDto[ id=" + id + " ]";
    }

}
