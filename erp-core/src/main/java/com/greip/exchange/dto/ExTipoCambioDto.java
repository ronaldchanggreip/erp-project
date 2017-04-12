package com.greip.exchange.dto;

import com.greip.core.dto.GeGenericDto;
import com.greip.core.dto.GeMonedaDto;
import com.greip.core.dto.GeParametroDto;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Entity
@Table(name = "EX_TIPO_CAMBIO")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "obtenerTiposCambioDelDia",
                procedureName = "P_EX_TIPO_CAMBIO",
                parameters = {
                        @StoredProcedureParameter(name = "P_FECHA", type = Date.class, mode = ParameterMode.IN)
                }),
        @NamedStoredProcedureQuery(
                name = "obtenerTipoCambioPorFecha",
                procedureName = "P_EX_TIPO_CAMBIO_02",
                parameters = {
                        @StoredProcedureParameter(name = "P_FECHA", type = Date.class, mode = ParameterMode.IN),
                        @StoredProcedureParameter(name = "P_MONEDA", type = BigInteger.class, mode = ParameterMode.IN)
                }, resultClasses = ExTipoCambioDto.class),
        @NamedStoredProcedureQuery(
                name = "inactivarOtrosTiposCambios",
                procedureName = "P_EX_TIPO_CAMBIO_01",
                parameters = {
                        @StoredProcedureParameter(name = "P_ID", type = BigInteger.class, mode = ParameterMode.IN)
                })
})
public class ExTipoCambioDto extends GeGenericDto implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_TIPO_CAMBIO")
    @TableGenerator(name = "SEQ_TIPO_CAMBIO", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "EX_TIPO_CAMBIO", allocationSize = 1)
    private BigInteger id;
    @Size(max = 1)
    @Column(name = "TIPO")
    private String tipo;
    @JoinColumn(name = "BANCO", referencedColumnName = "id")
    @ManyToOne
    private GeParametroDto bancoDto;
    @JoinColumn(name = "MONEDA_DESTINO", referencedColumnName = "id")
    @ManyToOne
    private GeMonedaDto monedaDestinoDto;
    @Column(name = "FACTOR")
    private BigDecimal factor;
    @JoinColumn(name = "MONEDA_ORIGEN", referencedColumnName = "id")
    @ManyToOne
    private GeMonedaDto monedaOrigenDto;
    @Column(name = "FECHA_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVigencia;
    @Column(name = "PRECIO_VENTA")
    private BigDecimal precioVenta;
    @Transient
    private BigDecimal precioVentaDif; // Tiene la diferencia del cambio respecto a la entidad ExEx Exchange Express
    @Column(name = "PRECIO_COMPRA")
    private BigDecimal precioCompra;

    @Column(name = "ESTADO")
    private String estado;

    @Transient
    private String estadoDesc; // Tiene la diferencia del cambio respecto a la entidad ExEx Exchange Express

    @Transient
    private String style; // Tiene la diferencia del cambio respecto a la entidad ExEx Exchange Express

    @Transient
    private BigDecimal precioCompraDif; // Tiene la diferencia del cambio respecto a la entidad ExEx Exchange Express

    @Transient
    private BigDecimal difRespectoExchange; // Tiene la diferencia del cambio respecto a la entidad ExEx Exchange Express

    @Transient
    private String tipoDesc;

    public ExTipoCambioDto(){}

    public ExTipoCambioDto(BigInteger id){this.id = id;}

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

    public GeParametroDto getBancoDto() {
        return bancoDto;
    }

    public void setBancoDto(GeParametroDto bancoDto) {
        this.bancoDto = bancoDto;
    }

    public GeMonedaDto getMonedaDestinoDto() {
        return monedaDestinoDto;
    }

    public void setMonedaDestinoDto(GeMonedaDto monedaDestinoDto) {
        this.monedaDestinoDto = monedaDestinoDto;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public GeMonedaDto getMonedaOrigenDto() {
        return monedaOrigenDto;
    }

    public void setMonedaOrigenDto(GeMonedaDto monedaOrigenDto) {
        this.monedaOrigenDto = monedaOrigenDto;
    }

    public String getTipoDesc() {
        if(tipo!=null && tipo.length()>0){
            if(tipo.equals("C"))
                tipoDesc = "Compra";
            else if(tipo.equals("V"))
                tipoDesc = "Venta";
        }
        return tipoDesc;
    }

    public void setTipoDesc(String tipoDesc) {
        this.tipoDesc = tipoDesc;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }


    public BigDecimal getDifRespectoExchange() {
        return difRespectoExchange;
    }

    public void setDifRespectoExchange(BigDecimal difRespectoExchange) {
        this.difRespectoExchange = difRespectoExchange;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioVentaDif() {
        return precioVentaDif;
    }

    public void setPrecioVentaDif(BigDecimal precioVentaDif) {
        this.precioVentaDif = precioVentaDif;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioCompraDif() {
        return precioCompraDif;
    }



    public void setPrecioCompraDif(BigDecimal precioCompraDif) {
        this.precioCompraDif = precioCompraDif;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoDesc() {
        if(estado!=null && estado.length()>0){
            if (this.estado.equals("A")){
                return "Activo";
            }else {
                return "Inactivo";
            }
        }else {
            return null;
        }
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public String getStyle() {
        if(estado!=null && estado.length()>0){
            if (this.estado.equals("A")){
                return "color: #2E7D32;font-weight: bold;"; //Verde
            }else {
                return "color: #e62a10";
            }
        }else {
            return null;
        }
    }

    public void setStyle(String style) {
        this.style = style;
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
        if (!(object instanceof ExTipoCambioDto)) {
            return false;
        }
        ExTipoCambioDto other = (ExTipoCambioDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.exchange.dto.ExTipoCambioDto[ id=" + id + " ]";
    }

}
