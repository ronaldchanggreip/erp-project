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
@Table(name = "SE_OPCION")
public class SeOpcionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_OPCION")
    @TableGenerator(name = "SEQ_OPCION", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "SE_OPCION", allocationSize = 1)
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "etiqueta")
    private String etiqueta;
    @Column(name = "icon")
    private String icon;
    @Column(name = "expanded_icon")
    private String expandedIcon;
    @Column(name = "action")
    private String action;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "orden")
    private BigInteger orden;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "padre", referencedColumnName = "id")
    @ManyToOne
    private SeOpcionDto padreDto;

    public SeOpcionDto() {
    }

    public SeOpcionDto(BigInteger id) {
        this.id = id;
    }

    public SeOpcionDto(BigInteger id, String nombre) {
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

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public SeOpcionDto getPadreDto() {
        return padreDto;
    }

    public void setPadreDto(SeOpcionDto padreDto) {
        this.padreDto = padreDto;
    }

    public String getExpandedIcon() {
        return expandedIcon;
    }

    public void setExpandedIcon(String expandedIcon) {
        this.expandedIcon = expandedIcon;
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
        if (!(object instanceof SeOpcionDto)) {
            return false;
        }
        SeOpcionDto other = (SeOpcionDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.SeOpcionDto[ id=" + id + " ]";
    }

}
