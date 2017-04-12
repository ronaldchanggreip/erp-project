package com.greip.core.controller;

import com.greip.core.dto.GeEntidadDto;
import com.greip.core.dto.GeMensajeControllerDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.service.GeEntidadService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/entidad/")
public class GeEntidadController {

    public static Logger log = Logger.getLogger(GeEntidadController.class);

    @Autowired
    GeEntidadService geEntidadService;


    @RequestMapping(value = "/gets",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<GeEntidadDto>> gets() {
        List<GeEntidadDto> lista = null;
        HttpStatus httpStatus = null;
        try {
            lista = (List<GeEntidadDto>) this.geEntidadService.gets(new GeEntidadDto());
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


        return new ResponseEntity<List<GeEntidadDto>>(lista, httpStatus);

    }


    @RequestMapping(value = "/getsId/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GeEntidadDto> getsById(@PathVariable("id") BigInteger id) {

        GeEntidadDto dto = null;
        HttpStatus httpStatus = null;

        if (id == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            try {
                dto = this.geEntidadService.getById(id);

                if (dto == null) {
                    httpStatus = HttpStatus.NOT_FOUND;
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
        }
        return new ResponseEntity<GeEntidadDto>(dto, httpStatus);
    }

    @RequestMapping(value = "/getsModulo/{modulo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeEntidadDto>> getsByModulo(@RequestParam("modulo") String modulo) {
        HttpStatus httpStatus = null;

        List<GeEntidadDto> lista = null;
        try {
            lista = (List<GeEntidadDto>) this.geEntidadService.getByModulo(modulo);

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
    public ResponseEntity<Object> save(@RequestBody GeEntidadDto dto) {
        HttpStatus httpStatus = null;

        GeMensajeControllerDto dtoMsj = null;


        if (dto == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            dtoMsj = new GeMensajeControllerDto(httpStatus.toString(), ServiceException.ENTIDAD_NOT_NULL, "La entidad no puede ser vacia");
        } else {
            try {
                geEntidadService.save(dto);
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
