package com.greip.exchange.dto;

import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Entity
@Table(name = "EX_SOLICITUD")
public class ExSolicitudDto extends GeGenericDto implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_SOLICITUD")
    @TableGenerator(name = "SEQ_SOLICITUD", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "EX_SOLICITUD", allocationSize = 1)
    private BigInteger id;
    @JoinColumn(name = "SOCIO_NEGOCIO", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeSocioNegocioDto socioNegocioDto;
    @Size(max = 2)
    @Column(name = "TRANSACCION")
    @Basic(optional = false)
    private String transaccion;
    @JoinColumn(name = "MONEDA_ORIGEN", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeMonedaDto monedaOrigenDto;
    @JoinColumn(name = "MONEDA_DESTINO", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeMonedaDto monedaDestinoDto;
    @JoinColumn(name = "CUENTA_BANCARIA_DESTINO", referencedColumnName = "id")
    @ManyToOne
    private GeSocioNegocioCuentaDto cBancariaDestinoDto;
    @JoinColumn(name = "TIPO_CAMBIO", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExTipoCambioDto tipoCambioDto;
    @Column(name = "IMPORTE_ORIGEN")
    @Basic(optional = false)
    private BigDecimal importeOrigen;
    @Column(name = "IMPORTE_DESTINO")
    @Basic(optional = false)
    private BigDecimal importeDestino;
    @Size(max = 200)
    @Column(name = "NRO_VOUCHER_ORIGEN")
    private String numVoucherOrigen;
    @Size(max = 200)
    @Column(name = "NRO_VOUCHER_DESTINO")
    private String numVoucherDestino;
    @JoinColumn(name = "BANCO_ORIGEN", referencedColumnName = "id")
    @ManyToOne
    private GeParametroDto bancoOrigenDto;
    @JoinColumn(name = "BANCO_DESTINO", referencedColumnName = "id")
    @ManyToOne
    private GeParametroDto bancoDestinoDto;
    @Column(name = "ETAPA")
    @Basic(optional = false)
    private String etapa;
    @Size(max = 2)
    @Column(name = "ESTADO")
    @Basic(optional = false)
    private String estado;
    @JoinColumn(name = "CUENTA_EMPRESA_ORIGEN", referencedColumnName = "id")
    @ManyToOne
    private ExCuentaEmpresaDto cEmpOrigenDto;
    @JoinColumn(name = "CUENTA_EMPRESA_DESTINO", referencedColumnName = "id")
    @ManyToOne
    private ExCuentaEmpresaDto cEmpDestinoDto;

    @JoinColumn(name = "BANCO_DESTINO_EMP", referencedColumnName = "id")
    @ManyToOne
    private GeParametroDto bancoDestinoEmpDto;



    @Transient
    private String estadoDesc;
    @Transient
    private String etapaDesc;
    @Transient
    private String transaccionDesc;
    @Transient
    private boolean flagConfirm = false;

    public ExSolicitudDto(){}

    public ExSolicitudDto(BigInteger id){
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public GeMonedaDto getMonedaOrigenDto() {
        return monedaOrigenDto;
    }

    public void setMonedaOrigenDto(GeMonedaDto monedaOrigenDto) {
        this.monedaOrigenDto = monedaOrigenDto;
    }

    public GeMonedaDto getMonedaDestinoDto() {
        return monedaDestinoDto;
    }

    public void setMonedaDestinoDto(GeMonedaDto monedaDestinoDto) {
        this.monedaDestinoDto = monedaDestinoDto;
    }

    public GeSocioNegocioCuentaDto getcBancariaDestinoDto() {
        return cBancariaDestinoDto;
    }

    public void setcBancariaDestinoDto(GeSocioNegocioCuentaDto cBancariaDestinoDto) {
        this.cBancariaDestinoDto = cBancariaDestinoDto;
    }

    public ExTipoCambioDto getTipoCambioDto() {
        return tipoCambioDto;
    }

    public void setTipoCambioDto(ExTipoCambioDto tipoCambioDto) {
        this.tipoCambioDto = tipoCambioDto;
    }

    public BigDecimal getImporteOrigen() {
        return importeOrigen;
    }

    public void setImporteOrigen(BigDecimal importeOrigen) {
        this.importeOrigen = importeOrigen;
    }

    public BigDecimal getImporteDestino() {
        return importeDestino;
    }

    public void setImporteDestino(BigDecimal importeDestino) {
        this.importeDestino = importeDestino;
    }

    public String getNumVoucherOrigen() {
        return numVoucherOrigen;
    }

    public void setNumVoucherOrigen(String numVoucherOrigen) {
        this.numVoucherOrigen = numVoucherOrigen;
    }

    public String getNumVoucherDestino() {
        return numVoucherDestino;
    }

    public void setNumVoucherDestino(String numVoucherDestino) {
        this.numVoucherDestino = numVoucherDestino;
    }

    public GeParametroDto getBancoOrigenDto() {
        return bancoOrigenDto;
    }

    public void setBancoOrigenDto(GeParametroDto bancoOrigenDto) {
        this.bancoOrigenDto = bancoOrigenDto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ExCuentaEmpresaDto getcEmpOrigenDto() {
        return cEmpOrigenDto;
    }

    public void setcEmpOrigenDto(ExCuentaEmpresaDto cEmpOrigenDto) {
        this.cEmpOrigenDto = cEmpOrigenDto;
    }

    public ExCuentaEmpresaDto getcEmpDestinoDto() {
        return cEmpDestinoDto;
    }

    public void setcEmpDestinoDto(ExCuentaEmpresaDto cEmpDestinoDto) {
        this.cEmpDestinoDto = cEmpDestinoDto;
    }

    public String getEstadoDesc() {
        if(estado!=null && estado.length()>0) {
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
        }
        return this.estadoDesc;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
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

    public GeSocioNegocioDto getSocioNegocioDto() {
        return socioNegocioDto;
    }

    public void setSocioNegocioDto(GeSocioNegocioDto socioNegocioDto) {
        this.socioNegocioDto = socioNegocioDto;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public GeParametroDto getBancoDestinoDto() {
        return bancoDestinoDto;
    }

    public void setBancoDestinoDto(GeParametroDto bancoDestinoDto) {
        this.bancoDestinoDto = bancoDestinoDto;
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public boolean isFlagConfirm() {
        return flagConfirm;
    }

    public void setFlagConfirm(boolean flagConfirm) {
        this.flagConfirm = flagConfirm;
    }

    public String getTransaccionDesc() {
        if(transaccion!=null){
            if(transaccion.equals("CD"))
                transaccionDesc = "Compra Dólares";
            else if(transaccion.equals("VD"))
                transaccionDesc = "Venta Dólares";
            else if(transaccion.equals("CE"))
                transaccionDesc = "Venta Euros";
            else if(transaccion.equals("VE"))
                transaccionDesc = "Venta Euros";
        }
        return transaccionDesc;
    }

    public void setTransaccionDesc(String transaccionDesc) {
        this.transaccionDesc = transaccionDesc;
    }

    public GeParametroDto getBancoDestinoEmpDto() {
        return bancoDestinoEmpDto;
    }

    public void setBancoDestinoEmpDto(GeParametroDto bancoDestinoEmpDto) {
        this.bancoDestinoEmpDto = bancoDestinoEmpDto;
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
        if (!(object instanceof ExSolicitudDto)) {
            return false;
        }
        ExSolicitudDto other = (ExSolicitudDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.exchange.dto.ExSolicitudDto[ id=" + id + " ]";
    }
}
