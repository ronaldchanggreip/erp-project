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
@Table(name = "GE_MONEDA")
public class GeMonedaDto extends GeGenericDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_GE_MONEDA")
    @TableGenerator(name = "SEQ_GE_MONEDA", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_MONEDA", allocationSize = 1)
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "nombre_corto")
    private String nombreCorto;
    @Basic(optional = false)
    @Column(name = "nombre_trx")
    private String nombreTrx;
    @Basic(optional = false)
    @Column(name = "simbolo")
    private String simbolo;
    @Basic(optional = false)
    @Column(name = "estado")

    private Boolean estado;
    @JoinColumn(name = "pais", referencedColumnName = "id")
    @ManyToOne
    private GeUbigeoDto paisDto;

    public GeMonedaDto() {
    }

    public GeMonedaDto(BigInteger id) {
        this.id = id;
    }

    public GeMonedaDto(BigInteger id, String nombre, String nombreCorto, String nombreTrx, String simbolo, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.nombreCorto = nombreCorto;
        this.nombreTrx = nombreTrx;
        this.simbolo = simbolo;
        this.estado = estado;
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

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombreTrx() {
        return nombreTrx;
    }

    public void setNombreTrx(String nombreTrx) {
        this.nombreTrx = nombreTrx;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public GeUbigeoDto getPaisDto() {
        return paisDto;
    }

    public void setPaisDto(GeUbigeoDto paisDto) {
        this.paisDto = paisDto;
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
        if (!(object instanceof GeMonedaDto)) {
            return false;
        }
        GeMonedaDto other = (GeMonedaDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.GeMonedaDto[ id=" + id + " ]";
    }

}
