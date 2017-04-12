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
@Table(name = "GE_PARAMETRO")
public class GeParametroDto extends GeGenericDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_GE_PARAMETRO")
    @TableGenerator(name = "SEQ_GE_PARAMETRO", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_PARAMETRO", allocationSize = 1)
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "clave")
    private String clave;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "tipo_dato")
    private String tipoDato;
    @Column(name = "valor")
    private String valor;
    @Basic(optional = false)
    @Column(name = "ind_defecto")
    private Boolean indDefecto;
    @Column(name = "descripcion_corta")
    private String descripcionCorta;
    @Column(name = "cod_homologacion")
    private String codHomologacion;
    @Column(name = "cod_estandar")
    private String codEstandar;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "grupo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeGrupoParametroDto grupoDto;

    @Transient
    private String estadoDesc;

    @Transient
    private String indDefectoDesc;

    @Transient
    private String tipoDatoDesc;

    public GeParametroDto() {
    }

    public GeParametroDto(BigInteger id) {
        this.id = id;
    }

    public GeParametroDto(BigInteger id, String clave, String descripcion, String tipoDato, Boolean indDefecto) {
        this.id = id;
        this.clave = clave;
        this.descripcion = descripcion;
        this.tipoDato = tipoDato;
        this.indDefecto = indDefecto;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getIndDefecto() {
        return indDefecto;
    }

    public void setIndDefecto(Boolean indDefecto) {
        this.indDefecto = indDefecto;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getCodHomologacion() {
        return codHomologacion;
    }

    public void setCodHomologacion(String codHomologacion) {
        this.codHomologacion = codHomologacion;
    }

    public String getCodEstandar() {
        return codEstandar;
    }

    public void setCodEstandar(String codEstandar) {
        this.codEstandar = codEstandar;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public GeGrupoParametroDto getGrupoDto() {
        return grupoDto;
    }

    public void setGrupoDto(GeGrupoParametroDto grupoDto) {
        this.grupoDto = grupoDto;
    }

    public String getEstadoDesc() {
        if(this.estado!=null) {
            if (this.estado) {
                return "Si";
            } else {
                return "No";
            }
        }
        return null;
    }

    public String getIndDefectoDesc() {
        if(this.indDefecto!=null) {
            if (this.indDefecto) {
                return "Si";
            } else {
                return "No";
            }
        }
        return null;
    }

    public void setIndDefectoDesc(String indDefectoDesc) {
        this.indDefectoDesc = indDefectoDesc;
    }

    public String getTipoDatoDesc() {
        if(this.tipoDato != null) {
            if (this.tipoDato.equals("C")) {
                return "Caracter";
            } else if (this.tipoDato.equals("B")) {
                return "Boolean";
            } else if (this.tipoDato.equals("D")) {
                return "Fecha";
            } else if (this.tipoDato.equals("N")) {
                return "Real";
            } else if (this.tipoDato.equals("E")) {
                return "Entero";
            } else {
                return "";
            }
        }
        return null;
    }

    public void setTipoDatoDesc(String tipoDatoDesc) {
        this.tipoDatoDesc = tipoDatoDesc;
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
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
        if (!(object instanceof GeParametroDto)) {
            return false;
        }
        GeParametroDto other = (GeParametroDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.GeParametroDto[ id=" + id + " ]";
    }

}
