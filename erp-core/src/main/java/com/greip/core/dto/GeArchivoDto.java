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
@Table(name = "GE_ARCHIVO")
public class GeArchivoDto extends GeGenericDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_GE_ARCHIVO")
    @TableGenerator(name = "SEQ_GE_ARCHIVO", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_ARCHIVO", allocationSize = 1)
    private BigInteger id;
    @Column(name = "registro")
    private BigInteger registro;
    @JoinColumn(name = "entidad", referencedColumnName = "id")
    @ManyToOne
    private GeEntidadDto entidadDto;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "ruta")
    private String ruta;
    @Column(name = "extension")
    private String extension;
    @Column(name = "peso")
    private Long peso;
    @Column(name = "estado", columnDefinition="tinyint(1) default 1")
    private Boolean estado;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "ind_es_atributo")
    private Boolean indEsAtributo;
    @Column(name = "content_type")
    private String contentType;

    @Transient
    private byte[] bites;

    public GeArchivoDto() {
    }

    public GeArchivoDto(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getRegistro() {
        return registro;
    }

    public void setRegistro(BigInteger registro) {
        this.registro = registro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Boolean getIndEsAtributo() {
        return indEsAtributo;
    }

    public void setIndEsAtributo(Boolean indEsAtributo) {
        this.indEsAtributo = indEsAtributo;
    }

    public GeEntidadDto getEntidadDto() {
        return entidadDto;
    }

    public void setEntidadDto(GeEntidadDto entidadDto) {
        this.entidadDto = entidadDto;
    }

    public byte[] getBites() {
        return bites;
    }

    public void setBites(byte[] bites) {
        this.bites = bites;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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
        if (!(object instanceof GeArchivoDto)) {
            return false;
        }
        GeArchivoDto other = (GeArchivoDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.GeArchivoDto[ id=" + id + " ]";
    }

}
