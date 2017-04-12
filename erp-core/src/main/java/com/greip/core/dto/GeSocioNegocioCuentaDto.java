package com.greip.core.dto;

import com.greip.core.constant.ConstantesCore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Entity
@Table(name = "GE_SOCIO_NEGOCIO_CUENTA")
public class GeSocioNegocioCuentaDto extends GeGenericDto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_SNEG_CUENTA")
    @TableGenerator(name = "SEQ_SNEG_CUENTA", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_SOCIO_NEGOCIO_CUENTA", allocationSize = 1)
    private BigInteger id;
    @JoinColumn(name = "SOCIO_NEGOCIO", referencedColumnName = "id")
    @ManyToOne
    private  GeSocioNegocioDto socioNegocioDto;
    @JoinColumn(name = "BANCO", referencedColumnName = "id")
    @ManyToOne
    private  GeParametroDto bancoDto;
    @JoinColumn(name = "MONEDA", referencedColumnName = "id")
    @ManyToOne
    private GeMonedaDto monedaDto;
    @Size(max = 100)
    @Column(name = "NUMERO_CUENTA")
    private String numCuenta;
    @Size(max = 100)
    @Column(name = "NUMERO_CUENTA_CII")
    private String numCuentaCII;
    @Size(max = 1)
    @Column(name = "ESTADO")
    private String estado;

    @Size(max = 2)
    @Column(name = "IND_CUENTA_VALIDA")
    private String indCuentaValida;

    @Transient
    private String indCuentaValidaDesc;

    @Transient
    private String estadoDesc;

    public GeSocioNegocioCuentaDto(){}

    public GeSocioNegocioCuentaDto(BigInteger id){this.id = id;}

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public GeParametroDto getBancoDto() {
        return bancoDto;
    }

    public void setBancoDto(GeParametroDto bancoDto) {
        this.bancoDto = bancoDto;
    }

    public GeMonedaDto getMonedaDto() {
        return monedaDto;
    }

    public void setMonedaDto(GeMonedaDto monedaDto) {
        this.monedaDto = monedaDto;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getNumCuentaCII() {
        return numCuentaCII;
    }

    public void setNumCuentaCII(String numCuentaCII) {
        this.numCuentaCII = numCuentaCII;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public GeSocioNegocioDto getSocioNegocioDto() {
        return socioNegocioDto;
    }

    public void setSocioNegocioDto(GeSocioNegocioDto socioNegocioDto) {
        this.socioNegocioDto = socioNegocioDto;
    }

    public String getEstadoDesc() {
        if(estado != null){
            if(estado.equals("A"))
                estadoDesc = "Activa";
            else if(estado.equals("I"))
                estadoDesc = "Inactiva";

        }
        return estadoDesc;
    }

    public String getIndCuentaValida() {
        return indCuentaValida;
    }

    public void setIndCuentaValida(String indCuentaValida) {
        this.indCuentaValida = indCuentaValida;
    }

    public String getIndCuentaValidaDesc() {
        if (this.indCuentaValida!=null) {
            if (this.indCuentaValida.equals(ConstantesCore.SocioNegocioCuenta.EST_PEN)){
                return ConstantesCore.SocioNegocioCuenta.EST_PEN_DESC;
            }else if (this.indCuentaValida.equals(ConstantesCore.SocioNegocioCuenta.EST_OBS)){
                return ConstantesCore.SocioNegocioCuenta.EST_OBS_DESC;
            }else if (this.indCuentaValida.equals(ConstantesCore.SocioNegocioCuenta.EST_VAL)){
                return ConstantesCore.SocioNegocioCuenta.EST_VAL_DESC;
            }else {
                return null;
            }

        }else {
            return null;
        }
    }

    public void setIndCuentaValidaDesc(String indCuentaValidaDesc) {
        this.indCuentaValidaDesc = indCuentaValidaDesc;
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
        if (!(object instanceof GeSocioNegocioCuentaDto)) {
            return false;
        }
        GeSocioNegocioCuentaDto other = (GeSocioNegocioCuentaDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.exchange.dto.GeSocioNegocioCuentaDto[ id=" + id + " ]";
    }
}
