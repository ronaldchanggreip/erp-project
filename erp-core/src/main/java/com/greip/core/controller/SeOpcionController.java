package com.greip.core.controller;

import com.google.gson.Gson;
import com.greip.core.dto.GeMensajeControllerDto;
import com.greip.core.dto.SeRolDto;
import com.greip.core.dto.TreeWebDto;
import com.greip.core.util.UtlController;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import com.greip.core.service.SeOpcionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrador on 17/02/2017.
 */
@RestController
@RequestMapping(value = "/api/opcion/")
public class SeOpcionController extends BaseController{
    public static Logger log = Logger.getLogger(SeOpcionController.class);

    @Autowired
    SeOpcionService service;

    @RequestMapping(value = "/getArbolWeb",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GeMensajeControllerDto> getArbolWeb() {
        List<SeRolDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        try {

            Gson gson = new Gson();
            List<TreeWebDto> treeWebDtos = service.generarArbol();
            if (treeWebDtos.isEmpty()) {
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(httpStatus.NO_CONTENT.value());
                respuesta.setRespuesta(lista);
            } else {
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(httpStatus.OK.value());
                respuesta.setRespuesta(treeWebDtos);
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
