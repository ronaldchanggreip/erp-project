package com.greip.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ronaldchang on 14/11/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Recurso no encontrado en la API-GREIP.CORE")
public class ControllerResourceNotFoundException extends RuntimeException {

    public ControllerResourceNotFoundException() {
    }

    public ControllerResourceNotFoundException(String descExcepcion) {
        super(descExcepcion);

    }

}
