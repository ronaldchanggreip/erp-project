package com.greip.exchange.dto;

import com.greip.core.dto.GeGenericDto;
import com.greip.core.dto.GeMonedaDto;
import com.greip.core.dto.GeParametroDto;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Entity
@Table(name = "EX_CUENTA_EMPRESA")
public class ExCuentaEmpresaDto extends GeGenericDto implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_CUENTA_EMPRESA")
    @TableGenerator(name = "SEQ_CUENTA_EMPRESA", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "EX_CUENTA_EMPRESA", allocationSize = 1)
    private BigInteger id;
    @JoinColumn(name = "BANCO", referencedColumnName = "id")
    @ManyToOne
    private GeParametroDto bancoDto;
    @JoinColumn(name = "MONEDA", referencedColumnName = "id")
    @ManyToOne
    private GeMonedaDto monedaDto;
    @Size(max = 200)
    @Column(name = "CUENTA")
    private String cuenta;
    @Size(max = 200)
    @Column(name = "CUENTA_I")
    private String cuentaInter;
    @Column(name = "SALDO")
    private BigDecimal saldo;
    @Size(max = 2)
    @Column(name = "ESTADO")
    private String estado;

    @Transient
    private String estadoDesc;

    public ExCuentaEmpresaDto(){}

    public ExCuentaEmpresaDto(BigInteger id){this.id = id;}

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

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoDesc() {
        if(estado!=null && estado.length()>0){
            if(estado.equals("A"))
                this.estadoDesc = "Activo";
            else if(estado.equals("I"))
                this.estadoDesc = "Inactivo";
            else if(estado.equals("S"))
                this.estadoDesc = "Suspendido";
        }
        return this.estadoDesc;
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public String getCuentaInter() {
        return cuentaInter;
    }

    public void setCuentaInter(String cuentaInter) {
        this.cuentaInter = cuentaInter;
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
        if (!(object instanceof ExCuentaEmpresaDto)) {
            return false;
        }
        ExCuentaEmpresaDto other = (ExCuentaEmpresaDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.exchange.dto.ExCuentaEmpresaDto[ id=" + id + " ]";
    }
}
