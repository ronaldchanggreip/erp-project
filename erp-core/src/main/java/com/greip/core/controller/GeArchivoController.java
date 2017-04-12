package com.greip.core.controller;

import com.google.gson.Gson;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.FileInfo;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeArchivoDto;
import com.greip.core.dto.GeMensajeControllerDto;
import com.greip.core.service.GeArchivoService;
import com.greip.core.util.UtlController;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/archivo/")
public class GeArchivoController extends BaseController{

    public static Logger log = Logger.getLogger(GeArchivoController.class);

    @Autowired
    GeArchivoService service;


    @RequestMapping(value = "/gets",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GeMensajeControllerDto> gets(@RequestParam("filtro") String filtro) {
        List<GeArchivoDto> lista = null;
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;
        try {
            Gson gson = new Gson();
            FiltroDto filtroDto = gson.fromJson(filtro, FiltroDto.class);
            lista = (List<GeArchivoDto>) this.service.gets(filtroDto);
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

    @RequestMapping(value = "/fileupload/{entidad}/{registro}", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ResponseEntity<GeMensajeControllerDto> upload(@RequestPart("file") MultipartFile[] files, @PathVariable("entidad") BigInteger entidad, @PathVariable("registro") BigInteger registro) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;
        FileInfo fileInfo = new FileInfo();
        HttpHeaders headers = new HttpHeaders();
        if (files!=null && files.length>0) {
            try{
                List<GeArchivoDto> lst = service.guardarArchivos(files, entidad, registro);
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                respuesta.setMensajeUsuario("Los archivos han sido guardados exitósamente!");
                respuesta.setRespuesta(lst);
            } catch (ServiceException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorService);
                respuesta.setDetalle(e.getMessage());
            } catch (Exception ex) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                respuesta = UtlController.getMesajeController(UtlController.codErrorController);
                respuesta.setDetalle(ex.getMessage());
            }
        }else{
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("No ha enviado ningún archivo!");
        }
        return ResponseEntity.status(httpStatus).body(respuesta);
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public HttpEntity<byte[]> download(@PathVariable("id") BigInteger id, final HttpServletRequest request, final HttpServletResponse response){

        File file = null;
        byte[] document = new byte[0];
        MediaType mediaType = null;
        try {
            GeArchivoDto archivoDto = service.getById(id);
            String contentType = archivoDto.getContentType();
            mediaType = new MediaType(contentType.split("/")[0], contentType.split("/")[1]);
            file = new File (archivoDto.getRuta());
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

    @RequestMapping(value = "/modificarVarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> modificarVarios(@RequestBody List<GeArchivoDto> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad no puede ser nula!");
        } else {
            try {
                for(GeArchivoDto dto: lst) {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                }
                lst = service.modificarVarios(lst);
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                respuesta.setRespuesta(lst);
                respuesta.setMensajeUsuario("El(los) archivo(s) ha(n) sido modificacod(s) correctamente");
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

    @RequestMapping(value = "/eliminarVarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<GeMensajeControllerDto> eliminarVarios(@RequestBody List<GeArchivoDto> lst, OAuth2Authentication auth) {
        HttpStatus httpStatus = null;
        GeMensajeControllerDto respuesta = null;

        if (lst == null) {
            httpStatus = HttpStatus.BAD_REQUEST;
            respuesta = UtlController.getMesajeController(UtlController.codErrorValidacion400);
            respuesta.setMensajeUsuario("La entidad no puede ser nula!");
        } else {
            try {
                for(GeArchivoDto dto: lst) {
                    //Obtenemos los datos de auditoria desde la seguridad
                    this.baseObtenerDatosAuditoria(auth, ConstantesCore.ValoresPorDefecto.ACCION_EDITAR, dto);
                }
                service.eliminarVarios(lst);
                httpStatus = HttpStatus.OK;
                respuesta = UtlController.getMesajeController(HttpStatus.OK.value());
                respuesta.setMensajeUsuario("El(los) archivo(s) ha(n) sido eliminado(s) correctamente");
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

