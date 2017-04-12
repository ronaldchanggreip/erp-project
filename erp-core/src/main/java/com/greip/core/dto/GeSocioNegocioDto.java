package com.greip.core.dto;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Entity
@Table(name = "GE_SOCIO_NEGOCIO")
public class GeSocioNegocioDto extends GeGenericDto implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_SOCIO_NEGOCIO")
    @TableGenerator(name = "SEQ_SOCIO_NEGOCIO", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "GE_SOCIO_NEGOCIO", allocationSize = 1)
    private BigInteger id;
    @JoinColumn(name = "TIPO_DOCUMENTO", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeParametroDto tipoDocumentoDto;
    @Size(max = 100)
    @Column(name = "NUMERO_DOCUMENTO")
    @Basic(optional = false)
    private String numDocumento;
    @Size(max = 100)
    @Column(name = "NOMBRES")
    private String nombres;
    @Size(max = 50)
    @Column(name = "APELLIDO_PATERNO")
    private String apPaterno;
    @Size(max = 50)
    @Column(name = "APELLIDO_MATERNO")
    private String apMaterno;
    @Size(max = 300)
    @Column(name = "RAZON_SOCIAL")
    private String razSocial;
    @Size(max = 300)
    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;
    @Column(name = "IND_CLIENTE")
    private Boolean indCliente;
    @Column(name = "IND_PROVEEDOR")
    private Boolean indProveedor;
    @Size(max = 50)
    @Column(name = "TELEFONO_PRINCIPAL")
    private String telefPrincipal;
    @Size(max = 10)
    @Column(name = "ANEXO_PRINCIPAL")
    private String anexoPrincipal;
    @Size(max = 50)
    @Column(name = "TELEFONO_SECUNDARIO")
    private String telefSecundario;
    @Size(max = 10)
    @Column(name = "ANEXO_SECUNDARIO")
    private String anexoSecundario;
    @Size(max = 50)
    @Column(name = "MOVIL_PRINCIPAL")
    private String movilPrincipal;
    @Size(max = 50)
    @Column(name = "MOVIL_SECUNDARIO")
    private String movilSecundario;
    @JoinColumn(name = "NACIONALIDAD", referencedColumnName = "id")
    @ManyToOne
    private GeUbigeoDto nacionalidadDto;
    @Size(max = 1)
    @Column(name = "ESTADO")
    private String estado;

    @Transient
    private String indClienteDesc;
    @Transient
    private String indProveedorDesc;
    @Transient
    private String estadoDesc;

    public GeSocioNegocioDto(){

    }

    public GeSocioNegocioDto(BigInteger id){this.id = id;}


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public GeParametroDto getTipoDocumentoDto() {
        return tipoDocumentoDto;
    }

    public void setTipoDocumentoDto(GeParametroDto tipoDocumentoDto) {
        this.tipoDocumentoDto = tipoDocumentoDto;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getRazSocial() {
        return razSocial;
    }

    public void setRazSocial(String razSocial) {
        this.razSocial = razSocial;
    }

    public Boolean getIndCliente() {
        return indCliente;
    }

    public void setIndCliente(Boolean indCliente) {
        this.indCliente = indCliente;
    }

    public Boolean getIndProveedor() {
        return indProveedor;
    }

    public void setIndProveedor(Boolean indProveedor) {
        this.indProveedor = indProveedor;
    }

    public String getTelefPrincipal() {
        return telefPrincipal;
    }

    public void setTelefPrincipal(String telefPrincipal) {
        this.telefPrincipal = telefPrincipal;
    }

    public String getAnexoPrincipal() {
        return anexoPrincipal;
    }

    public void setAnexoPrincipal(String anexoPrincipal) {
        this.anexoPrincipal = anexoPrincipal;
    }

    public String getTelefSecundario() {
        return telefSecundario;
    }

    public void setTelefSecundario(String telefSecundario) {
        this.telefSecundario = telefSecundario;
    }

    public String getAnexoSecundario() {
        return anexoSecundario;
    }

    public void setAnexoSecundario(String anexoSecundario) {
        this.anexoSecundario = anexoSecundario;
    }

    public String getMovilPrincipal() {
        return movilPrincipal;
    }

    public void setMovilPrincipal(String movilPrincipal) {
        this.movilPrincipal = movilPrincipal;
    }

    public String getMovilSecundario() {
        return movilSecundario;
    }

    public void setMovilSecundario(String movilSecundario) {
        this.movilSecundario = movilSecundario;
    }

    public GeUbigeoDto getNacionalidadDto() {
        return nacionalidadDto;
    }

    public void setNacionalidadDto(GeUbigeoDto nacionalidadDto) {
        this.nacionalidadDto = nacionalidadDto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getIndClienteDesc() {
        if(this.indCliente!=null) {
            if (this.indCliente) {
                return "Si";
            } else {
                return "No";
            }
        }
        return null;
    }

    public void setIndClienteDesc(String indClienteDesc) {
        this.indClienteDesc = indClienteDesc;
    }

    public String getIndProveedorDesc() {
        if(this.indProveedor!=null) {
            if (this.indProveedor) {
                return "Si";
            } else {
                return "No";
            }
        }
        return null;
    }

    public void setIndProveedorDesc(String indProveedorDesc) {
        this.indProveedorDesc = indProveedorDesc;
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
        }
        return estadoDesc;
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
        if (!(object instanceof SeUsuarioDto)) {
            return false;
        }
        GeSocioNegocioDto other = (GeSocioNegocioDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.exchange.dto.GeSocioNegocioDto[ id=" + id + " ]";
    }
}
