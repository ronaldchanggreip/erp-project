package com.greip.core.controller;

import com.google.gson.Gson;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.*;
import com.greip.core.service.SeUsuarioService;
import com.greip.core.util.UtlController;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/usuario/")
public class SeUsuarioController extends BaseController{

    public static Logger log = Logger.getLogger(SeUsuarioController.class);

    @Autowired
    SeUsuarioService service;

    @RequestMapping(value = "/gets",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GeMensajeControllerDto> gets(@RequestParam("filtro") String filtro) {
        List<SeUsuarioDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        try {

            Gson gson = new Gson();
            FiltroDto filtroDto = gson.fromJson(filtro, FiltroDto.class);
            //System.out.println(gson.toJson(filtroDto));
            lista = (List<SeUsuarioDto>) this.service.gets(filtroDto);
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
    public ResponseEntity<GeMensajeControllerDto> getsById(@PathVariable("id") BigInteger id) {
        SeUsuarioDto dto = null;
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

    @RequestMapping(value = "/getsLoginEmail/{loginEmail}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GeMensajeControllerDto> getsByLoginEmail(@PathVariable("loginEmail") String loginEmail) {
        SeUsuarioDto dto = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (loginEmail == null || loginEmail.length()==0) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("El id de la entidad no puede ser nulo!");

        } else {
            try {
                //System.out.println("IDENTIFICADOR : " + loginEmail);
                List<SeUsuarioDto> lst = this.service.getByLoginEmail(loginEmail);
                if(lst!=null && !lst.isEmpty()) {
                    dto = lst.get(0);
                    dto.setContrasena(null);
                }

                if (dto == null) {
                    httpStatus = HttpStatus.OK;
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
    public ResponseEntity<GeMensajeControllerDto> save(@RequestBody SeUsuarioDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto respuesta = null;
        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad a crear no puede ser nulo");
        } else {
            if(dto.getId()!=null){
                httpStatus = HttpStatus.BAD_REQUEST;
                respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
                respuesta.setMensajeUsuario("El identificador de la entidad que desea crear ya existe!");
            }else{
                try {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoriaNoObj(auth, ConstantesCore.ValoresPorDefecto.ACCION_NUEVO, dto);
                    int retorno = service.save(dto);
                    if(retorno == 1){
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                        respuesta.setMensajeUsuario("El Usuario se guardó correctamente!");
                        respuesta.setRespuesta(dto);
                    }else if(retorno == 2){
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                        respuesta.setMensajeUsuario("El login o email ya están siendo usados!");
                        respuesta.setRespuesta(dto);
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

    @RequestMapping(value = "/registro-login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> registroLogin(@RequestBody SeUsuarioDto dto) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad a crear no puede ser nulo");
        } else {
            if(dto.getId()!=null){
                httpStatus = HttpStatus.BAD_REQUEST;
                respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
                respuesta.setMensajeUsuario("El identificador de la entidad que desea crear ya existe!");
            }else{
                try {
                    //Obtenemos los datos de auditoria desde la seguridad
                    int retorno = service.save(dto);
                    if(retorno == 1){
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                        respuesta.setMensajeUsuario("El Usuario se guardó correctamente!");
                        respuesta.setRespuesta(dto);
                    }else if(retorno == 2){
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                        respuesta.setMensajeUsuario("El login o email ya están siendo usados!");
                        respuesta.setRespuesta(dto);
                    }else if(retorno == 3){
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                        respuesta.setMensajeUsuario("El email registrado "+ dto.getEmail() + "no es válido!");
                        respuesta.setRespuesta(dto);
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

    @RequestMapping(value = "/save/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@PathVariable("id") BigInteger id, @RequestBody SeUsuarioDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad de actualizar no puede ser nulo!");
        } else {
            try {
                //Obtenemos los datos de auditoria desde la seguridad
                this.baseObtenerDatosAuditoriaNoObj(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                int retorno = service.save(dto);
                if(retorno == 1){
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("El Usuario se guardó correctamente!");
                    respuesta.setRespuesta(dto);
                }else if(retorno == 2){
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("El login o email ya están siendo usados!");
                    respuesta.setRespuesta(dto);
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

    @RequestMapping(value = "/save-perfil/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> savePerfil(@PathVariable("id") BigInteger id, @RequestBody SeUsuarioDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad de actualizar no puede ser nulo!");
        } else {
            try {
                //Obtenemos los datos de auditoria desde la seguridad
                this.baseObtenerDatosAuditoriaNoObj(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                int retorno = service.savePerfil(dto);
                if(retorno == 1){
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("El Usuario se guardó correctamente!");
                    respuesta.setRespuesta(dto);
                }else if(retorno == 2){
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario("El login o email ya están siendo usados!");
                    respuesta.setRespuesta(dto);
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

    @RequestMapping(value = "/eliminar-logico", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> eliminarLogico(@RequestBody List<BigInteger> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto respuesta = null;
        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("Debe enviar al menos un id para realizar la eliminación!");
        } else {
            try {
                AuditoriaDto auditoriaDto = new AuditoriaDto();
                //Obtenemos los datos de auditoria desde la seguridad
                this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, auditoriaDto);
                service.eliminarLogico(lst, auditoriaDto);
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                respuesta.setMensajeUsuario("El usuario ha sido eliminado!");
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

    @RequestMapping(value = "/recuperar-contrasena", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody String loginEmail) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (loginEmail == null || loginEmail.length() == 0) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("El login o email no pueden ser nulos!");
        } else {
            try {
                RptaServiceDto resultado = service.recuperarContrasena(loginEmail);
                if(resultado.isOk()) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario(resultado.getMensaje());
                }else{
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                    respuesta.setMensajeUsuario(resultado.getMensaje());
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
}
