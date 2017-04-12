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
@Table(name = "GE_GRUPO_PARAMETRO")
public class GeGrupoParametroDto extends GeGenericDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_GE_GRUPO_PARAMETRO")
    @TableGenerator(name = "SEQ_GE_GRUPO_PARAMETRO", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_GRUPO_PARAMETRO", allocationSize = 1)
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Transient
    private String tipoDesc;

    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public GeGrupoParametroDto() {
    }

    public GeGrupoParametroDto(BigInteger id) {
        this.id = id;
    }

    public GeGrupoParametroDto(BigInteger id, String tipo, String nombre) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDesc() {
        if(this.tipo != null) {
            if (this.tipo.equals("S")) {
                return "Sistema";
            } else if (this.tipo.equals("N")) {
                return "Negocio";
            } else {
                return "";
            }
        }
        return null;
    }

    public void setTipoDesc(String tipoDesc) {
        this.tipoDesc = tipoDesc;
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
        if (!(object instanceof GeGrupoParametroDto)) {
            return false;
        }
        GeGrupoParametroDto other = (GeGrupoParametroDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.GeGrupoParametroDto[ id=" + id + " ]";
    }

}
