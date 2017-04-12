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
@Table(name = "GE_UBIGEO")
public class GeUbigeoDto extends GeGenericDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_GE_UBIGEO")
    @TableGenerator(name = "SEQ_GE_UBIGEO", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_UBIGEO", allocationSize = 1)
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "nombre_corto")
    private String nombreCorto;
    @Basic(optional = false)
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "cod_postal")
    private String codPostal;
    @Column(name = "cod_inei")
    private String codInei;
    @Column(name = "cod_sunat")
    private String codSunat;
    @Column(name = "estado")
    private Boolean estado;

    @JoinColumn(name = "padre", referencedColumnName = "id")
    @ManyToOne
    private GeUbigeoDto padreDto;

    public GeUbigeoDto() {
    }

    public GeUbigeoDto(BigInteger id) {
        this.id = id;
    }

    public GeUbigeoDto(BigInteger id, String nombre, String nombreCompleto, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.nombreCompleto = nombreCompleto;
        this.tipo = tipo;
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getCodInei() {
        return codInei;
    }

    public void setCodInei(String codInei) {
        this.codInei = codInei;
    }

    public String getCodSunat() {
        return codSunat;
    }

    public void setCodSunat(String codSunat) {
        this.codSunat = codSunat;
    }

    public GeUbigeoDto getPadreDto() {
        return padreDto;
    }

    public void setPadreDto(GeUbigeoDto padreDto) {
        this.padreDto = padreDto;
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
        if (!(object instanceof GeUbigeoDto)) {
            return false;
        }
        GeUbigeoDto other = (GeUbigeoDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.GeUbigeoDto[ id=" + id + " ]";
    }

}
