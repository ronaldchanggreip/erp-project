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
@Table(name = "SE_USUARIO")
public class SeUsuarioDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_USUARIO")
    @TableGenerator(name = "SEQ_USUARIO", table = "GE_ENTIDAD", pkColumnName = "NOMBRE", valueColumnName = "CORRELATIVO", pkColumnValue = "SE_USUARIO", allocationSize = 1)
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = true)
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "fec_vigencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecVigencia;
    @Column(name = "ind_bloqueado")
    private Boolean indBloqueado;
    @Column(name = "estado")
    private Boolean estado;
    @Basic(optional = false)
    @Column(name = "conf_cant_reg")
    private int confCantReg = 20;
    @Basic(optional = false)
    @Column(name = "conf_idioma")
    private String confIdioma = "ES";
    @Column(name = "socio_negocio")
    private BigInteger socioNegocio;
    @JoinColumn(name = "rol", referencedColumnName = "id")
    @ManyToOne
    private SeRolDto rolDto;

    //Campos de auditoria
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "terminal")
    private String terminal;
    @Column(name = "usuario")
    private BigInteger usuario;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "terminal_creacion")
    private String terminalCreacion;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "usuario_creacion")
    private BigInteger usuarioCreacion;

    @Column(name = "ELIMINADO")
    private Boolean eliminado = Boolean.FALSE;

    @Column(name = "IND_EMAIL_VALIDO")
    private String indEmailValido = "P";

    @Transient
    private GeParametroDto tipoDocumentoDto;
    @Transient
    private String numDocumento;
    @Transient
    private String nombres;
    @Transient
    private String apPaterno;
    @Transient
    private String apMaterno;
    @Transient
    private String razSocial;
    @Transient
    private Boolean cambiarPassword = false;

    @Transient
    private String indEmailValidoDesc;

    public SeUsuarioDto() {
    }

    public SeUsuarioDto(BigInteger id) {
        this.id = id;
    }

    public SeUsuarioDto(BigInteger id, String login, String nombre, String email, String contrasena, int confCantReg, String confIdioma) {
        this.id = id;
        this.login = login;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.confCantReg = confCantReg;
        this.confIdioma = confIdioma;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFecVigencia() {
        return fecVigencia;
    }

    public void setFecVigencia(Date fecVigencia) {
        this.fecVigencia = fecVigencia;
    }

    public Boolean getIndBloqueado() {
        return indBloqueado;
    }

    public void setIndBloqueado(Boolean indBloqueado) {
        this.indBloqueado = indBloqueado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public int getConfCantReg() {
        return confCantReg;
    }

    public void setConfCantReg(int confCantReg) {
        this.confCantReg = confCantReg;
    }

    public String getConfIdioma() {
        return confIdioma;
    }

    public void setConfIdioma(String confIdioma) {
        this.confIdioma = confIdioma;
    }

    public BigInteger getSocioNegocio() {
        return socioNegocio;
    }

    public void setSocioNegocio(BigInteger socioNegocio) {
        this.socioNegocio = socioNegocio;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public BigInteger getUsuario() {
        return usuario;
    }

    public void setUsuario(BigInteger usuario) {
        this.usuario = usuario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTerminalCreacion() {
        return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
        this.terminalCreacion = terminalCreacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigInteger getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(BigInteger usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public SeRolDto getRolDto() {
        return rolDto;
    }

    public void setRolDto(SeRolDto rolDto) {
        this.rolDto = rolDto;
    }

    public Boolean getCambiarPassword() {
        return cambiarPassword;
    }

    public void setCambiarPassword(Boolean cambiarPassword) {
        this.cambiarPassword = cambiarPassword;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getIndEmailValido() {
        return indEmailValido;
    }

    public void setIndEmailValido(String indEmailValido) {
        this.indEmailValido = indEmailValido;
    }

    public String getIndEmailValidoDesc() {
        if (this.indEmailValido!=null) {
            if (this.indEmailValido.equals(ConstantesCore.Usuario.EST_PEN)){
                return ConstantesCore.Usuario.EST_PEN_DESC;
            }else if (this.indEmailValido.equals(ConstantesCore.Usuario.EST_OBS)){
                return ConstantesCore.Usuario.EST_OBS_DESC;
            }else if (this.indEmailValido.equals(ConstantesCore.Usuario.EST_VAL)){
                return ConstantesCore.Usuario.EST_VAL_DESC;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    public void setIndEmailValidoDesc(String indEmailValidoDesc) {
        this.indEmailValidoDesc = indEmailValidoDesc;
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
        SeUsuarioDto other = (SeUsuarioDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.com.greip.dto.SeUsuarioDto[ id=" + id + " ]";
    }

}
