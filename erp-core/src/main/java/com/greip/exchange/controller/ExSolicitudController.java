package com.greip.exchange.controller;

import com.google.gson.Gson;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.controller.BaseController;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeMensajeControllerDto;
import com.greip.core.dto.SeUsuarioDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.util.UtlController;
import com.greip.exchange.dto.ExSolicitudDto;
import com.greip.exchange.service.ExSolicitudService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
@RestController
@RequestMapping(value = "/api/solicitud/")
public class ExSolicitudController extends BaseController {
    public static Logger log = Logger.getLogger(ExSolicitudController.class);

    @Autowired
    ExSolicitudService service;

    @RequestMapping(value = "/gets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> gets(@RequestParam("filtro") String filtro, OAuth2Authentication auth) {
        List<ExSolicitudDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;
        try {
            SeUsuarioDto seUsuarioDto = this.baseObtenerUsuarioPorLogin(this.baseObtenerLoginDelToken(auth));
            Gson gson = new Gson();
            FiltroDto filtroDto = gson.fromJson(filtro, FiltroDto.class);
            lista = (List<ExSolicitudDto>) this.service.gets(filtroDto, seUsuarioDto);
            if (lista.isEmpty()) {
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(httpStatus.NO_CONTENT.value());
                respuesta.setRespuesta(lista);
            } else {
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(httpStatus.OK.value());
                respuesta.setRespuesta(lista);
            }
        } catch (ServiceException es) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            respuesta = UtlController.getMesajeController(UtlController.codErrorService);
            respuesta.setDetalle(es.getMessage());
        } catch (Exception ex) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            respuesta = UtlController.getMesajeController(UtlController.codErrorController);
            respuesta.setDetalle(ex.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/getsId/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> getsById(@PathVariable("id") BigInteger id) {

        ExSolicitudDto dto = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (id == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("El id de la entidad no puede ser nulo!");
        } else {
            try {
                dto = this.service.getById(id);

                if (dto == null) {
                    httpStatus = HttpStatus.NOT_FOUND;
                    respuesta = UtlController.getMesajeController(httpStatus.NOT_FOUND.value());
                } else {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(httpStatus.OK.value());
                    respuesta.setRespuesta(dto);
                }
            } catch (ServiceException es) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorService);
                respuesta.setDetalle(es.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> save(@RequestBody ExSolicitudDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad no puede ser nula!");
        } else {
            if(dto.getId()!=null){
                httpStatus = HttpStatus.BAD_REQUEST;
                respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
                respuesta.setMensajeUsuario("La entidad enviada ya existe!");
            }else{
                try {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_NUEVO, dto);
                    int resultado = service.nuevaSolicitud(dto);
                    if(resultado == 1) {
                            httpStatus = HttpStatus.OK;
                            respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                            respuesta.setMensajeUsuario("La Solicitud se guardó correctamente!");
                            respuesta.setRespuesta(dto);
                    }else if(resultado == 2) {
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                        respuesta.setMensajeUsuario("El socio de negocio cuenta ya con una solicitud en etapa REGISTRO y estado PENDIENTE!");
                    }else if(resultado == 3) {
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(UtlController.codErrorValidacionNoBlock200);
                        respuesta.setMensajeUsuario("El tipo de cambio ha cambiado, ¿Desea continuar?");
                    }
                } catch (ServiceException e) {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorService);
                    respuesta.setDetalle(e.getMessage());
                } catch (Exception ex) {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                    respuesta.setDetalle(ex.getMessage());
                }
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/actualizarPorSocio/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> actualizarPorSocio(@PathVariable("id") BigInteger id, @RequestBody ExSolicitudDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad no puede ser nula!");
        } else {
            if(id == null){
                httpStatus = HttpStatus.BAD_REQUEST;
                respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
                respuesta.setMensajeUsuario("El identificador de la entidad no puede nulo!");
            }else{
                try {
                    dto.setId(id);
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                    int resultado = service.actualizarPorSocio(dto);
                    if(resultado == 1) {
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                        respuesta.setRespuesta(dto);
                    }else if(resultado == 2) {
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                        respuesta.setMensajeUsuario("La solicitud se encuentra en una etapa y estado en la que no puede ser modificada!");
                    }
                } catch (ServiceException e) {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorService);
                    respuesta.setDetalle(e.getMessage());
                } catch (Exception ex) {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                    respuesta.setDetalle(ex.getMessage());
                }
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/actualizarPorExchange/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> actualizarPorExchange(@PathVariable("id") BigInteger id, @RequestBody ExSolicitudDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad no puede ser nula!");
        } else {
            if(id == null){
                httpStatus = HttpStatus.BAD_REQUEST;
                respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
                respuesta.setMensajeUsuario("El identificador de la entidad no puede nulo!");
            }else{
                try {
                    dto.setId(id);
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                    int resultado = service.actualizarPorExchange(dto);
                    if(resultado == 1) {
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                        respuesta.setRespuesta(dto);
                    }else if(resultado == 2) {
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                        respuesta.setMensajeUsuario("La solicitud se encuentra en una etapa y estado en la que no puede ser modificada");
                    }
                } catch (ServiceException e) {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorService);
                    respuesta.setDetalle(e.getMessage());
                } catch (Exception ex) {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                    respuesta.setDetalle(ex.getMessage());
                }
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/anular", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> anular(@RequestBody List<ExSolicitudDto> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad no puede ser nula!");
        } else {
            try {
                for(ExSolicitudDto dto: lst) {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                }
                String resultado = service.anular(lst);
                if(resultado.equals("1")){
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido anulada(s) correctamente");
                }else{
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido anulada(s) a excepción de la(s) que tiene(n) como código:" + resultado);
                }
            } catch (ServiceException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorService);
                respuesta.setDetalle(e.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/abortar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> abortar(@RequestBody List<ExSolicitudDto> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La lista no puede ser nula!");
        } else {
            try {
                for(ExSolicitudDto dto: lst) {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                }
                String resultado = service.abortar(lst);
                if(resultado.equals("1")) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido abortada(s) correctamente");
                }else{
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido abortada(s) a excepción de la(s) que tiene(n) como código:" + resultado);
                }
            } catch (ServiceException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorService);
                respuesta.setDetalle(e.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/observarEnRevision", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> observarEnRevision(@RequestBody List<ExSolicitudDto> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La lista no puede ser nula!");
        } else {
            try {
                for(ExSolicitudDto dto: lst) {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                }
                String resultado = service.observarEnRevision(lst);
                if(resultado.equals("1")) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido observada(s) correctamente");
                }else{
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido observada(s) a excepción de la(s) que tiene(n) como código:" + resultado);
                }
            } catch (ServiceException ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/levantarObservacion/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> levantarObservacion(@PathVariable("id") BigInteger id, @RequestBody ExSolicitudDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad no puede ser nula!");
        } else {
            try {
                if(id!=null)
                    dto.setId(id);
                //Obtenemos los datos de auditoria desde la seguridad
                this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);

                int resultado = service.levantarObservacion(dto);
                if(resultado == 1) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("Las observaciones han sido levantadas correctamente!");
                }else if(resultado == 2) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("La Solicitud no se encuentra observada");
                }
            } catch (ServiceException ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/revisada", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> revisada(@RequestBody List<ExSolicitudDto> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La lista no puede ser nula!");
        } else {
            try {
                for(ExSolicitudDto dto: lst) {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                }
                String resultado = service.revisada(lst);
                if(resultado.equals("1")) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido revisada(s) correctamente");
                }else{
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido revisada(s) a excepción de la(s) que tiene(n) como código:" + resultado);
                }
            } catch (ServiceException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(e.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/observarEnValidacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> observarEnValidacion(@RequestBody List<ExSolicitudDto> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La lista no puede ser nula!");
        } else {
            try {
                for(ExSolicitudDto dto: lst) {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                }
                String resultado = service.observarEnValidacion(lst);
                if(resultado.equals("1")) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido observada(s) correctamente");
                }else{
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido observada(s) a excepción de la(s) que tiene(n) como código:" + resultado);
                }
            } catch (ServiceException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(e.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/validada", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> validada(@RequestBody List<ExSolicitudDto> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La lista no puede ser nula!");
        } else {
            try {
                for(ExSolicitudDto dto: lst) {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                }
                String resultado = service.validada(lst);
                if(resultado.equals("1")) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido validada(s) correctamente");
                }else{
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("La(s) solicitud(es) ha(n) sido validada(s) a excepción de la(s) que tiene(n) como código:" + resultado);
                }
            } catch (ServiceException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(e.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/ejecutada/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> ejecutada(@PathVariable("id") BigInteger id, @RequestBody ExSolicitudDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad no puede ser nula!");
        } else {
            try {
                if(id!=null)
                    dto.setId(id);
                //Obtenemos los datos de auditoria desde la seguridad
                this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);

                int resultado = service.ejecutada(dto);
                if(resultado == 1) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("La solicitud ha sido ejecutada correctamente!");
                }else if(resultado == 2) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("La Solicitud se encuentra en una etapa y estado en la que no puede marcarse como ejecutada");
                }
            } catch (ServiceException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(e.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/generarPDF/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public HttpEntity<byte[]> generarPDF(@PathVariable("id") BigInteger id, final HttpServletRequest request, final HttpServletResponse response){

        File file = null;
        byte[] document = new byte[0];
        MediaType mediaType = null;
        try {
            file = service.generarPDF(id);
            //System.out.println("Generamos el pdf");
            String contentType = "application/pdf";
            mediaType = new MediaType(contentType.split("/")[0], contentType.split("/")[1]);
            document = FileCopyUtils.copyToByteArray(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.set("Content-Disposition", "inline; filename=" + file.getName());
        header.setContentLength(document.length);

        return new HttpEntity<byte[]>(document, header);
    }
}
