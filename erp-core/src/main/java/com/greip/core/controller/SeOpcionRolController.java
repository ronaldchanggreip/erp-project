package com.greip.core.controller;

import com.google.gson.Gson;
import com.greip.core.dto.SeOpcionRolDto;
import com.greip.core.service.SeOpcionRolService;
import com.greip.core.util.UtlController;
import com.greip.core.dto.GeMensajeControllerDto;
import com.greip.core.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/opcionrol/")
public class SeOpcionRolController {

    public static Logger log = Logger.getLogger(SeOpcionRolController.class);

    @Autowired
    SeOpcionRolService service;


    @RequestMapping(value = "/gets",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<SeOpcionRolDto>> gets() {
        List<SeOpcionRolDto> lista = null;
        HttpStatus httpStatus = null;
        try {
            lista = (List<SeOpcionRolDto>) this.service.gets(new SeOpcionRolDto());
            if (lista.isEmpty()) {
                httpStatus = HttpStatus.OK;
            } else {
                httpStatus = HttpStatus.OK;
            }


        } catch (ServiceException es) {
            log.fatal(es);
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception ex) {
            log.fatal(ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(lista);

    }


    @RequestMapping(value = "/getsId/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getsById(@PathVariable("id") BigInteger id) {

        SeOpcionRolDto dto = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto dtoMsj = null;

        if (id == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ServiceException.ID_NOT_NULL, "El ID no puede ser nulo");
        } else {
            try {
                dto = this.service.getById(id);

                if (dto == null) {
                    httpStatus = HttpStatus.NOT_FOUND;
                    dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ServiceException.REQUEST_NO_FOUND, "La consulta no devolvió ningun registro");
                } else {
                    httpStatus = HttpStatus.OK;
                    return ResponseEntity.status(httpStatus).body(dto);
                }

            } catch (ServiceException es) {
                log.fatal(es);
                httpStatus = HttpStatus.BAD_REQUEST;
                dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), es.getCause().getMessage(), es.getCause().getCause().getMessage());
            } catch (Exception ex) {
                log.fatal(ex);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ex.getCause().getMessage(), ex.getCause().getCause().getMessage());
            }
        }
        return ResponseEntity.status(httpStatus).body(dtoMsj);

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> save(@RequestBody SeOpcionRolDto dto) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto dtoMsj = null;

        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ServiceException.ENTIDAD_NOT_NULL, "La entidad no puede ser vacia");
        } else {
            if(dto.getId()!=null){
                httpStatus = HttpStatus.BAD_REQUEST;
                dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ServiceException.ENTIDAD_NOT_NULL, "La entidad enviada ya existe");
            }else{
                try {
                    service.save(dto);
                    httpStatus = HttpStatus.OK;
                    return new ResponseEntity<Object>(dto, httpStatus);
                } catch (ServiceException e) {
                    log.error(e);
                    httpStatus = HttpStatus.BAD_REQUEST;
                    dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), e.getCause().getMessage(), e.getCause().getCause().getMessage());
                } catch (Exception ex) {
                    log.fatal(ex);
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ex.getCause().getMessage(), ex.getCause().getCause().getMessage());
                }
            }
        }
        return new ResponseEntity<Object>(dtoMsj, httpStatus);
    }

    @RequestMapping(value = "/save/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@PathVariable("id") BigInteger id, @RequestBody SeOpcionRolDto dto) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto dtoMsj = null;


        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ServiceException.ENTIDAD_NOT_NULL, "La entidad no puede ser vacia");
        } else {
            try {
                if(id!=null)
                    dto.setId(id);
                service.save(dto);
                httpStatus = HttpStatus.OK;
                return new ResponseEntity<Object>(dto, httpStatus);
            } catch (ServiceException e) {
                log.error(e);
                httpStatus = HttpStatus.BAD_REQUEST;
                dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), e.getCause().getMessage(), e.getCause().getCause().getMessage());
            } catch (Exception ex) {
                log.fatal(ex);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ex.getCause().getMessage(), ex.getCause().getCause().getMessage());
            }
        }
        return new ResponseEntity<Object>(dtoMsj, httpStatus);

    }


    @RequestMapping(value = "/getsOpcionesRol/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getsOpcionesRol(@PathVariable("id") BigInteger id) {
        SeOpcionRolDto dto = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (id == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("El id de la entidad no puede ser nulo!");
        } else {
            try {
                List<Object> lst = this.service.opcionesParaMenu(id);
                Gson gson = new Gson();
                //System.out.println("json : " + gson.toJson(lst));
                if (lst.isEmpty()) {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(httpStatus.NO_CONTENT.value());
                    respuesta.setRespuesta(lst);
                } else {
                    httpStatus = HttpStatus.OK;
                    respuesta = UtlController.getMesajeController(httpStatus.OK.value());
                    respuesta.setRespuesta(lst);
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
}
