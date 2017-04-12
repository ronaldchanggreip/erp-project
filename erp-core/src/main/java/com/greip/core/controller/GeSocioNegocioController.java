package com.greip.core.controller;

import com.google.gson.Gson;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeMensajeControllerDto;
import com.greip.core.dto.GeSocioNegocioDto;
import com.greip.core.util.UtlController;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import com.greip.core.service.GeSocioNegocioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
@RestController
@RequestMapping(value = "/api/socio-negocio/")
public class GeSocioNegocioController extends BaseController{

    public static Logger log = Logger.getLogger(GeSocioNegocioController.class);

    @Autowired
    GeSocioNegocioService service;

    @RequestMapping(value = "/gets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> gets(@RequestParam("filtro") String filtro) {
        List<GeSocioNegocioDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        try {

            Gson gson = new Gson();
            FiltroDto filtroDto = gson.fromJson(filtro, FiltroDto.class);
            //System.out.println(gson.toJson(filtroDto));
            lista = (List<GeSocioNegocioDto>) this.service.gets(filtroDto);
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

        GeSocioNegocioDto dto = null;
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

    @RequestMapping(value = "/getsByDocumento/{tipo}/{numero}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> getsByDocumento(@PathVariable("tipo") BigInteger tipo, @PathVariable("numero") String numero) {

        GeSocioNegocioDto dto = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (tipo == null || numero == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("El tipo y el número de documento no pueden ser nulos!");

        } else {
            try {
                dto = this.service.getByDocumento(tipo, numero);

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

    @RequestMapping(value = "/getsByDocumentoPublic/{tipo}/{numero}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> getsByDocumentoPublic(@PathVariable("tipo") BigInteger tipo, @PathVariable("numero") String numero) {

        GeSocioNegocioDto dto = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (tipo == null || numero == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("El tipo y el número de documento no pueden ser nulos!");

        } else {
            try {
                dto = this.service.getByDocumento(tipo, numero);

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

    @RequestMapping(value = "/getsUsuario/{usuario}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> getsByUsuario(@PathVariable("usuario") String usuario) {
        GeSocioNegocioDto dto = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (usuario == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("El usuario no puede ser nulo!");
        } else {
            try {
                dto = this.service.getByUsuario(usuario);

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
    public ResponseEntity<Object> save(@RequestBody GeSocioNegocioDto dto, OAuth2Authentication auth) {
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
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_NUEVO, dto);
                    int retorno = service.save(dto);
                    if(retorno == 1){
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                        respuesta.setMensajeUsuario("El Socio de Negocio se guardó correctamente!");
                        respuesta.setRespuesta(dto);
                    }else{
                        httpStatus = HttpStatus.OK;
                        respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion200);
                        respuesta.setMensajeUsuario("Ya existe un socio de negocio con este tipo y número de documento");
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
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> save(@PathVariable("id") BigInteger id, @RequestBody GeSocioNegocioDto dto, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto respuesta = null;

        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad de actualizar no puede ser nulo!");
        } else {
            try {
                if(id!=null)
                    dto.setId(id);
                service.save(dto);
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                respuesta.setMensajeUsuario("El Socio de Negocio se guardó correctamente!");
                respuesta.setRespuesta(dto);
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
                respuesta.setMensajeUsuario("El socio de negocio ha sido eliminado!");
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
