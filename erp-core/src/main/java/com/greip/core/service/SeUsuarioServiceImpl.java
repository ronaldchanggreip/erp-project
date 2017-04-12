package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.*;
import com.greip.core.repository.SeUsuarioRepository;
import com.greip.core.util.*;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import com.greip.core.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Service("geUsuarioService")
@Transactional
public class SeUsuarioServiceImpl implements SeUsuarioService {

    @Resource
    SeUsuarioRepository dao;

    @Resource
    SeUsuarioRolService seUsuarioRolService;

    @Resource
    SeOpcionRolService seOpcionRolService;
    @Resource
    GeSocioNegocioService geSocNegoService;
    @Resource
    GeCorreoElectronicoService geCorreoService;

    @Autowired
    private Environment env;


    @Override
    @ExceptionServiceAnnotation
    public int save(SeUsuarioDto dto) throws ServiceException {
        int retorno = 1;
        try {
            if (dto != null) {
                //Verificamos que el login e email no exista
                List<SeUsuarioDto> lstValid = null;
                if (dto.getId() == null) {
                    lstValid = getByLoginEmail(dto.getLogin());
                    if (lstValid != null && !lstValid.isEmpty())
                        retorno = 2;
                    lstValid = getByLoginEmail(dto.getEmail());
                    if (lstValid != null && !lstValid.isEmpty())
                        retorno = 2;
                } else {
                    lstValid = getByLoginEmailExcepto(dto.getId(), dto.getLogin());
                    if (lstValid != null && !lstValid.isEmpty())
                        retorno = 2;
                    lstValid = getByLoginEmailExcepto(dto.getId(), dto.getEmail());
                    if (lstValid != null && !lstValid.isEmpty())
                        retorno = 2;
                }
                if (retorno == 1) {
                    String newPassword = null;
                    //Veirificamos si se trata del perfil web
                    dto.setSocioNegocio(null);
                    if (dto.getRolDto().getId().equals(ConstantesCore.Perfil.ROL_WEB)) {
                        //Verificamos si el socio de negocio existe
                        GeSocioNegocioDto socNegoDto = geSocNegoService.getByDocumento(dto.getTipoDocumentoDto().getId(), dto.getNumDocumento());
                        if (socNegoDto == null) {
                            //Si el socio de negocio no existe lo creamos
                            GeSocioNegocioDto socNegoNewDto = new GeSocioNegocioDto();
                            socNegoNewDto.setTipoDocumentoDto(dto.getTipoDocumentoDto());
                            socNegoNewDto.setApPaterno(dto.getApPaterno());
                            socNegoNewDto.setApMaterno(dto.getApMaterno());
                            socNegoNewDto.setNombres(dto.getNombres());
                            socNegoNewDto.setRazSocial(dto.getRazSocial());
                            socNegoNewDto.setNumDocumento(dto.getNumDocumento());

                            if(dto.getUsuario()!=null) {
                                socNegoNewDto.setUsuarioCreacionDto(new SeUsuarioDto(dto.getUsuario()));
                                socNegoNewDto.setFechaCreacion(UtlDate.obtenerFechaActualDate());
                                socNegoNewDto.setTerminalCreacion(dto.getTerminal());
                                socNegoNewDto.setUsuarioDto(new SeUsuarioDto(dto.getUsuario()));
                                socNegoNewDto.setFecha(UtlDate.obtenerFechaActualDate());
                                socNegoNewDto.setTerminal(dto.getTerminal());
                            }

                            socNegoNewDto.setIndProveedor(Boolean.FALSE);
                            socNegoNewDto.setIndCliente(Boolean.TRUE);
                            socNegoNewDto.setEstado("A");
                            geSocNegoService.save(socNegoNewDto);
                            dto.setSocioNegocio(socNegoNewDto.getId());
                        }else{
                            //System.out.println("socNegoDto.getId() : " + socNegoDto.getId());
                            dto.setSocioNegocio(socNegoDto.getId());
                        }
                    }
                    if (dto.getId() != null) { //Si edita
                        if (dto.getCambiarPassword()) { //Si se cambia de contrasena
                            newPassword = dto.getContrasena();
                            dto.setContrasena(UtlSecurity.passwordEncoder(dto.getContrasena()));

                        }else { // Si no se cambia la contrasena se mantiene con la misma no se envia correo
                            SeUsuarioDto seUsuOldDto = getById(dto.getId());
                            newPassword = seUsuOldDto.getContrasena();
                            dto.setContrasena(seUsuOldDto.getContrasena());
                        }
                    } else { //Si es nuevo
                        newPassword = dto.getContrasena();
                        dto.setContrasena(UtlSecurity.passwordEncoder(dto.getContrasena()));
                    }

                    /*Enviar correo al registrar usuario o al cambiar password*/
                    if (dto.getId() != null && dto.getCambiarPassword() && newPassword !=null) { // Si se edita y se cambia de password
                        //Generamos los datos del correo
                        GeCorreoDto geCorreoDto = new GeCorreoDto();
                        geCorreoDto.setAsunto("Exchange Express - Nuevas Credenciales");
                        geCorreoDto.setCuerpo(cuerpoEmailRecuperarContrasena(dto.getNombre(),dto.getLogin(), dto.getEmail(),newPassword));

                        String[] to = new String[1];
                        to[0] = dto.getEmail();
                        geCorreoDto.setTo(to);
                        geCorreoService.enviar(geCorreoDto);
                    }else if (dto.getId() == null && newPassword !=null) { // Si es un nuevo usuario
                        //Generamos los datos del correo
                        GeCorreoDto geCorreoDto = new GeCorreoDto();
                        geCorreoDto.setAsunto("Exchange Express - Registro de Usuario");
                        geCorreoDto.setCuerpo(cuerpoEmailRegistroUsuario(dto.getNombre(),dto.getLogin(),dto.getEmail(),newPassword));

                        String[] to = new String[1];
                        to[0] = dto.getEmail();
                        geCorreoDto.setTo(to);
                        geCorreoService.enviar(geCorreoDto);
                    }

                    dao.save(dto);


                }
            } else {
                throw (new ServiceException(ServiceException.ERROR_DB));
            }
        }catch(Exception e){
            try {
                throw  e;
            } catch (UnsupportedEncodingException e1) {
                retorno = 3;
                e1.printStackTrace();
            } catch (MessagingException e1) {
                retorno = 3;
                e1.printStackTrace();
            }
        }
        return retorno;
    }

    @Override
    @ExceptionServiceAnnotation
    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException {
        for(BigInteger id : lst){
            SeUsuarioDto dto = getById(id);
            dto.setFecha(auditoriaDto.getFecha());
            dto.setUsuario(auditoriaDto.getUsuarioDto().getId());
            dto.setTerminal(auditoriaDto.getTerminal());
            dto.setEliminado(Boolean.TRUE);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public int savePerfil(SeUsuarioDto dto) throws ServiceException {
        int retorno = 1;
        try {
            if (dto != null) {
                //Verificamos que el login e email no exista
                List<SeUsuarioDto> lstValid = null;
                if (dto.getId() == null) {
                    lstValid = getByLoginEmail(dto.getLogin());
                    if (lstValid != null && !lstValid.isEmpty())
                        retorno = 2;
                    lstValid = getByLoginEmail(dto.getEmail());
                    if (lstValid != null && !lstValid.isEmpty())
                        retorno = 2;
                } else {
                    lstValid = getByLoginEmailExcepto(dto.getId(), dto.getLogin());
                    if (lstValid != null && !lstValid.isEmpty())
                        retorno = 2;
                    lstValid = getByLoginEmailExcepto(dto.getId(), dto.getEmail());
                    if (lstValid != null && !lstValid.isEmpty())
                        retorno = 2;
                }

                if (retorno == 1) {
                    String newPassword = null;
                    if (dto.getId() != null) {
                        if (dto.getCambiarPassword()) {
                            newPassword = dto.getContrasena();
                            dto.setContrasena(UtlSecurity.passwordEncoder(dto.getContrasena()));
                        }else {
                            SeUsuarioDto seUsuOldDto = getById(dto.getId());
                            dto.setContrasena(seUsuOldDto.getContrasena());
                        }
                    } else {
                        newPassword = dto.getContrasena();
                        dto.setContrasena(UtlSecurity.passwordEncoder(dto.getContrasena()));
                    }

                    /*Enviar correo al registrar usuario o al cambiar password*/
                    if (dto.getId() != null && dto.getCambiarPassword() && newPassword !=null) { // Si se edita y se cambia de password
                        //Generamos los datos del correo
                        GeCorreoDto geCorreoDto = new GeCorreoDto();
                        geCorreoDto.setAsunto("Exchange Express - Nuevas Credenciales");
                        geCorreoDto.setCuerpo(cuerpoEmailRecuperarContrasena(dto.getNombre(),dto.getLogin(), dto.getEmail(),newPassword));

                        String[] to = new String[1];
                        to[0] = dto.getEmail();
                        geCorreoDto.setTo(to);
                        geCorreoService.enviar(geCorreoDto);
                    }else if (dto.getId() == null && newPassword !=null) { // Si es un nuevo usuario
                        //Generamos los datos del correo
                        GeCorreoDto geCorreoDto = new GeCorreoDto();
                        geCorreoDto.setAsunto("Exchange Express - Registro de Usuario");
                        geCorreoDto.setCuerpo(cuerpoEmailRegistroUsuario(dto.getNombre(),dto.getLogin(),dto.getEmail(),newPassword));

                        String[] to = new String[1];
                        to[0] = dto.getEmail();
                        geCorreoDto.setTo(to);
                        geCorreoService.enviar(geCorreoDto);
                    }

                    dao.save(dto);
                }
            } else {
                throw (new ServiceException(ServiceException.ERROR_DB));
            }
        }catch(Exception e){
            try {
                throw  e;
            } catch (UnsupportedEncodingException e1) {
                retorno = 3;
                e1.printStackTrace();
            } catch (MessagingException e1) {
                retorno = 3;
                e1.printStackTrace();
            }
        }
        return retorno;
    }

    @Override
    public SeUsuarioDto getById(BigInteger id) throws ServiceException {
        if (id != null) {
            SeUsuarioDto dto = dao.findOne(id);
            if(dto!=null){
                if(dto.getSocioNegocio() != null) {
                    GeSocioNegocioDto socNegoDto = geSocNegoService.getById(dto.getSocioNegocio());
                    dto.setTipoDocumentoDto(socNegoDto.getTipoDocumentoDto());
                    dto.setNumDocumento(socNegoDto.getNumDocumento());
                    dto.setApPaterno(socNegoDto.getApPaterno());
                    dto.setApMaterno(socNegoDto.getApMaterno());
                    dto.setNombres(socNegoDto.getNombres());
                    dto.setRazSocial(socNegoDto.getRazSocial());
                }
            }
            return dto;
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }

    }

    @Override
    @ExceptionServiceAnnotation
    public List<SeUsuarioDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<SeUsuarioDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from SeUsuarioDto dto ";
            if(filtroTransDto!=null){
                if(filtroTransDto.getCondicion().length()>0) {
                    query += "where " + filtroTransDto.getCondicion();
                    lstParams = filtroTransDto.getParams();
                }
                if(filtroDto.isOrder() && filtroTransDto.getOrder().length()>0)
                    query += " order by " + filtroTransDto.getOrder();
            }
            lista = dao.ejecutarQuery(query, lstParams);
        }catch(Exception e){
            throw e;
        }
        return lista;
    }

    @Override
    public List<SeUsuarioDto> getByLoginEmail(String loginEmail) throws ServiceException {

        if (loginEmail != null) {
            return (List<SeUsuarioDto>) dao.getByLoginEmail(loginEmail);
        } else {
            throw (new ServiceException("El login o email no pueden ser nulo"));
        }
    }

    @Override
    public List<SeUsuarioDto> getByLoginEmailExcepto(BigInteger id, String loginEmail) throws ServiceException {

        if (loginEmail != null) {
            return (List<SeUsuarioDto>) dao.getByLoginEmailExcepto(id, loginEmail);
        } else {
            throw (new ServiceException("El login o email no pueden ser nulo"));
        }
    }


    @Override
    public List<SeOpcionDto> getOpcionesActivasByUsuario(BigInteger idUsuario) throws ServiceException {
        List<SeOpcionDto> listaRetorno = new ArrayList<SeOpcionDto>();

        List<SeUsuarioRolDto> roles = seUsuarioRolService.getByUsuario(idUsuario);

        for (SeUsuarioRolDto ur : roles) {
            // Solo permisos activos
            if (ur.getEstado()) {
                List<SeOpcionRolDto> opcionesRol = seOpcionRolService.getByRol(ur.getId());
                for (SeOpcionRolDto or : opcionesRol) {
                    if (or.getEstado() && or.getOpcionDto().getEstado()) {
                        if (existeOpcionEnListaOpciones(listaRetorno, or.getOpcionDto()))
                            listaRetorno.add(or.getOpcionDto());
                    }
                }
            }
        }

        return listaRetorno;
    }

    @Override
    public RptaServiceDto recuperarContrasena(String loginEmail) throws ServiceException {
        RptaServiceDto resultado = null;
        String email = "";
        try {
            //Obtenemos los datos del usuario
            List<SeUsuarioDto> lst = this.getByLoginEmail(loginEmail);
            if(lst!=null && !lst.isEmpty()){
                SeUsuarioDto seUsuarioDto = lst.get(0);
                email = seUsuarioDto.getEmail();
                String contrasena = UtlCaracter.getCadenaAlfanumAleatoria(10);
                seUsuarioDto.setContrasena(UtlSecurity.passwordEncoder(contrasena));

                //Generamos los datos del correo
                GeCorreoDto geCorreoDto = new GeCorreoDto();
                geCorreoDto.setAsunto("Exchange Express - Nuevas Credenciales");
                geCorreoDto.setCuerpo(cuerpoEmailRecuperarContrasena(seUsuarioDto.getNombre(),seUsuarioDto.getLogin(), seUsuarioDto.getEmail(),contrasena));

                String[] to = new String[1];
                to[0] = seUsuarioDto.getEmail();
                geCorreoDto.setTo(to);
                geCorreoService.enviar(geCorreoDto);

                dao.save(seUsuarioDto);

                resultado = new RptaServiceDto(true, "Estimado usuario en breve le enviaremos un correo con sus nuevas credenciales.");
            }else
                resultado = new RptaServiceDto(false, "El login y/o email no estan registrados");
        }catch (Exception e){
            try {
                throw  e;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                resultado = new RptaServiceDto(false, "El email "+ email+" registrado no es válido");
            } catch (MessagingException e1) {
                resultado = new RptaServiceDto(false, "El email "+ email+" registrado no es válido");
                e1.printStackTrace();
            }
        }
        return resultado;
    }

    public String cuerpoEmailRecuperarContrasena(String nombreUsuario, String loginUsuario, String emailUsuario, String contrasena) {
        String rutaStyle = env.getProperty("path.application") + "generales" + File.separator + "email.css";
        String titulo = "EXCHANGE EXPRESS - Nuevas Credenciales";
        String saludo = "Estimado "+nombreUsuario+",";
        String parrafoPrincipal = "El sistema le ha generado nuevas credenciales para acceder al sistema :";
        String parrafoSecundario = "";
        GeClaveValorCorreoDto cvUsuario = new GeClaveValorCorreoDto("Usuario",loginUsuario);
        GeClaveValorCorreoDto cvEmail = new GeClaveValorCorreoDto("Email",emailUsuario);
        GeClaveValorCorreoDto cvPassword = new GeClaveValorCorreoDto("Password",contrasena);
        String parrafoFinal = "Sistema Exchange Express";
        String link = "https://exex.pe/exchange-app";

        return UtlMail.plantillaHtmlEmail(rutaStyle,titulo,saludo,parrafoPrincipal,parrafoSecundario,parrafoFinal,link, cvUsuario,cvEmail, cvPassword);
    }

    public String cuerpoEmailRegistroUsuario(String nombreUsuario, String loginUsuario, String emailUsuario, String contrasena) {
        String rutaStyle = env.getProperty("path.application") + "generales" + File.separator + "email.css";
        String titulo = "EXCHANGE EXPRESS - Registro de Usuario";
        String saludo = "Estimado "+nombreUsuario+",";
        String parrafoPrincipal = "El se ha creado su usuario para ingresar a nuestra plataforma web la cual le permitirá generar sus solicitudes de cambio de divisas.";
        String parrafoSecundario = "Le recordamos que puede ingresar al Sistema Exchange Express con su Login o Email; a continuación le enviamos sus credenciales:";
        GeClaveValorCorreoDto cvUsuario = new GeClaveValorCorreoDto("Usuario",loginUsuario);
        GeClaveValorCorreoDto cvEmail = new GeClaveValorCorreoDto("Email",emailUsuario);
        GeClaveValorCorreoDto cvPassword = new GeClaveValorCorreoDto("Password",contrasena);
        String parrafoFinal = "Sistema Exchange Express";
        String link = "https://exex.pe/exchange-app";

        return UtlMail.plantillaHtmlEmail(rutaStyle,titulo,saludo,parrafoPrincipal,parrafoSecundario,parrafoFinal,link, cvUsuario,cvEmail, cvPassword);
    }


    private Boolean existeOpcionEnListaOpciones(List<SeOpcionDto> l, SeOpcionDto o) {
        Boolean existe = Boolean.FALSE;

        for (SeOpcionDto op : l) {
            if (op.getId().equals(o.getId())) {
                existe = Boolean.TRUE;
                break;
            }
        }

        return existe;
    }
}
