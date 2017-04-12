package com.greip.core.controller;

import com.google.gson.Gson;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeMensajeControllerDto;
import com.greip.core.dto.GeParametroDto;
import com.greip.core.service.GeParametroService;
import com.greip.core.util.UtlController;
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
@RequestMapping(value = "/api/parametro/")
public class GeParametroController extends BaseController{

    public static Logger log = Logger.getLogger(GeParametroController.class);

    @Autowired
    GeParametroService service;

    //@PreAuthorize("hasAuthority('Sistemas')")
    @RequestMapping(value = "/gets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> gets(@RequestParam("filtro") String filtro) {
        List<GeParametroDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;
        try {
            Gson gson = new Gson();
            FiltroDto filtroDto = gson.fromJson(filtro, FiltroDto.class);
            lista = (List<GeParametroDto>) this.service.gets(filtroDto);
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
        GeParametroDto dto = null;
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

    @RequestMapping(value = "/getsGrupo/{grupo}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> getsGrupo(@PathVariable("grupo") BigInteger grupo) {
        List<GeParametroDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;
        if (grupo == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("El grupo buscado no puede ser nulo!");
        }else {
            try {
                lista = (List<GeParametroDto>) this.service.getsActivosGrupo(grupo);
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
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> save(@RequestBody GeParametroDto dto, OAuth2Authentication auth) {
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
                    service.save(dto);
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                    respuesta.setMensajeUsuario("El Parámetro se guardó correctamente!");
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
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }


    @RequestMapping(value = "/save/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> save(@PathVariable("id") BigInteger id, @RequestBody GeParametroDto dto, OAuth2Authentication auth) {
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
                respuesta.setMensajeUsuario("El Parámetro se guardó correctamente!");
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
                if(lst.size()==1)
                    respuesta.setMensajeUsuario("El parámetro ha sido eliminado!");
                else
                    respuesta.setMensajeUsuario("Los parámetros han sido eliminados!");
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

    @RequestMapping(value = "/getsNumDocumento",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> getsNumDocumento(

    ) {
        List<GeParametroDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;
        try {

            lista = (List<GeParametroDto>) this.service.getsActivosGrupo(ConstantesCore.GrupoParametro.GRUPO_NUM_DOCUMENTO);
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
}
