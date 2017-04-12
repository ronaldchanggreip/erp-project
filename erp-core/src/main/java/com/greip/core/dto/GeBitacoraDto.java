/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.greip.core.dto;

import com.greip.core.constant.ConstantesCore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author mdp_changr
 */
@Entity
@Table(name = "GE_BITACORA")
public class GeBitacoraDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_GE_BITACORA")
    @TableGenerator(name = "SEQ_GE_BITACORA", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_BITACORA", allocationSize = 1)
    private BigInteger id;
    @Column(name = "registro")
    private BigInteger registro;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "tip_movimiento")
    private String tipMovimiento;
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne
    private SeUsuarioDto usuarioDto;
    @Column(name = "estado")
    private String estado;
    @Column(name = "terminal")
    private String terminal;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "etapa")
    private String etapa;
    @JoinColumn(name = "entidad", referencedColumnName = "id")
    @ManyToOne
    private GeEntidadDto entidadDto;

    @Transient
    private String tipoMovimientoDesc;
    @Transient
    private String estadoDesc;
    @Transient
    private String etapaDesc;


    public GeBitacoraDto() {
    }

    public GeBitacoraDto(BigInteger id) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipMovimiento() {
        return tipMovimiento;
    }

    public void setTipMovimiento(String tipMovimiento) {
        this.tipMovimiento = tipMovimiento;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public GeEntidadDto getEntidadDto() {
        return entidadDto;
    }

    public void setEntidadDto(GeEntidadDto entidadDto) {
        this.entidadDto = entidadDto;
    }

    public SeUsuarioDto getUsuarioDto() {
        return usuarioDto;
    }

    public void setUsuarioDto(SeUsuarioDto usuarioDto) {
        this.usuarioDto = usuarioDto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getTipoMovimientoDesc() {
        if(tipMovimiento != null){
            if(tipMovimiento.equals(ConstantesCore.ValoresPorDefecto.MOVIM_BITACORA_COD_ALTA))
                this.tipoMovimientoDesc = ConstantesCore.ValoresPorDefecto.MOVIM_BITACORA_DESC_ALTA;
            else if(tipMovimiento.equals(ConstantesCore.ValoresPorDefecto.MOVIM_BITACORA_COD_MODIF))
                this.tipoMovimientoDesc = ConstantesCore.ValoresPorDefecto.MOVIM_BITACORA_DESC_MODIF;
        }
        return tipoMovimientoDesc;
    }

    public void setTipoMovimientoDesc(String tipoMovimientoDesc) {
        this.tipoMovimientoDesc = tipoMovimientoDesc;
    }

    public String getEstadoDesc() {
        if(entidadDto != null && estado != null && estado.length() > 0) {
            if (entidadDto.getId().equals(ConstantesCore.Entidad.SOLICITUD)) {
                if (estado.equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_PENDIENTE))
                    this.estadoDesc = ConstantesCore.ValoresPorDefecto.ESTSOL_DESC_PENDIENTE;
                else if (estado.equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_OBSERVADA))
                    this.estadoDesc = ConstantesCore.ValoresPorDefecto.ESTSOL_DESC_OBSERVADA;
                else if (estado.equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ANULADA))
                    this.estadoDesc = ConstantesCore.ValoresPorDefecto.ESTSOL_DESC_ANULADA;
                else if (estado.equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ABORTADA))
                    this.estadoDesc = ConstantesCore.ValoresPorDefecto.ESTSOL_DESC_ABORTADA;
                else if (estado.equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_REVISADA))
                    this.estadoDesc = ConstantesCore.ValoresPorDefecto.ESTSOL_DESC_REVISADA;
                else if (estado.equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_EJECUTADA))
                    this.estadoDesc = ConstantesCore.ValoresPorDefecto.ESTSOL_DESC_EJECUTADA;
                else if (estado.equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_VALIDADA))
                    this.estadoDesc = ConstantesCore.ValoresPorDefecto.ESTSOL_DESC_VALIDADA;
            }else {
                if (estado.equals("A") || estado.equals("1"))
                    this.estadoDesc = "Activo";
                else if (estado.equals("I") || estado.equals("0"))
                    this.estadoDesc = "Inactivo";
            }

        }
        return estadoDesc;
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public String getEtapaDesc() {
        if(etapa!=null && etapa.length()>0) {
            if (etapa.equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REGISTRO))
                this.etapaDesc = ConstantesCore.ValoresPorDefecto.ETAPASOL_DESC_REGISTRO;
            else if (etapa.equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REVISION))
                this.etapaDesc = ConstantesCore.ValoresPorDefecto.ETAPASOL_DESC_REVISION;
            else if (etapa.equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_VALIDACION))
                this.etapaDesc = ConstantesCore.ValoresPorDefecto.ETAPASOL_DESC_VALIDACION;
            else if (etapa.equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_EJECUCION))
                this.etapaDesc = ConstantesCore.ValoresPorDefecto.ETAPASOL_DESC_EJECUCION;

        }
        return etapaDesc;
    }

    public void setEtapaDesc(String etapaDesc) {
        this.etapaDesc = etapaDesc;
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
        if (!(object instanceof GeBitacoraDto)) {
            return false;
        }
        GeBitacoraDto other = (GeBitacoraDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.GeBitacoraDto[ id=" + id + " ]";
    }

}
