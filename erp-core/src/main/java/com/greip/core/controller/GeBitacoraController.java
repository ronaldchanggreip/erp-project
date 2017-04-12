package com.greip.core.controller;

import com.google.gson.Gson;
import com.greip.core.dto.FiltroDto;
import com.greip.core.util.UtlController;
import com.greip.core.dto.GeBitacoraDto;
import com.greip.core.dto.GeMensajeControllerDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.service.GeBitacoraService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/bitacora/")
public class GeBitacoraController {

    public static Logger log = Logger.getLogger(GeBitacoraController.class);

    @Autowired
    GeBitacoraService service;


    @RequestMapping(value = "/gets",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GeMensajeControllerDto> gets(@RequestParam("filtro") String filtro) {
        List<GeBitacoraDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;
        try {
            Gson gson = new Gson();
            FiltroDto filtroDto = gson.fromJson(filtro, FiltroDto.class);
            lista = (List<GeBitacoraDto>) this.service.gets(filtroDto);
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
    public ResponseEntity<Object> getsById(@PathVariable("id") BigInteger id) {

        GeBitacoraDto dto = null;
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

    @RequestMapping(value = "/getsByEntidadRegistro/{entidad}/{registro}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeBitacoraDto>> getsByModulo(@RequestParam("entidad") BigInteger idEntidad, @RequestParam("registro") BigInteger registro) {
        HttpStatus httpStatus = null;

        List<GeBitacoraDto> lista = null;
        try {
            lista = (List<GeBitacoraDto>) this.service.gets(idEntidad, registro);

            if (lista == null || lista.isEmpty()) {
                httpStatus = HttpStatus.OK;
            } else {
                httpStatus = HttpStatus.OK;
            }
        } catch (ServiceException es) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception ex) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(httpStatus).body(lista);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody GeBitacoraDto dto) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto dtoMsj = null;


        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ServiceException.ENTIDAD_NOT_NULL, "La entidad no puede ser vacia");
        } else {
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
        return new ResponseEntity<Object>(dtoMsj, httpStatus);

    }

}
