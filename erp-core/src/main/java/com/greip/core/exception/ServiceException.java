package com.greip.core.exception;

/**
 * Created by ronaldchang on 14/11/16.
 */
public class ServiceException extends Exception {

    public static String TRANSACCION_VALIDA = "La transacción se ha llevado a cabo sin problemas";
    public static String ENTIDAD_NOT_NULL = "La entidad no puede ser nulo";
    public static String ID_NOT_NULL = "El id no puede ser nulo";
    public static String ERROR_DB = "Error de base de datos";
    public static String REQUEST_NO_FOUND = "No se encontró la entidad";
    public static String REQUEST_VALIDA_ERROR = "Error de validación por regla de negocio";

    public ServiceException(String descException) {
        super(descException);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String m, Exception e) {
        super(m, e);
    }
}
