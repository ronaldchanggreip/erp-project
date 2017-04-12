package com.greip.core.util;

import com.greip.core.dto.GeMensajeControllerDto;

/**
 * Created by ronaldchang on 15/11/16.
 */
public class UtlController {

 //   public static HttpStatus httpStatusErrorService = HttpStatus.valueOf(599);

    public static int codErrorController = 599; //Error no identificado en la capa de controller
    public static int codErrorService = 598; //Error no identificado de la capa de servicios
    public static int codErrorValidacion200 = 291; //Rpta satisfactoria con validación de negocio
    public static int codErrorValidacion400 = 491; //Error en el request, datos necesarios no enviados
    public static int codErrorValidacionNoBlock200 = 292; //Rpta statisfactoria con error de validación de negocio no bloqueante

    public static int codCorrectoConObs = 290; //Finalización correcta con observaciones


    public static GeMensajeControllerDto getMesajeController(int status) {
        //System.out.println(status);
        if (status == 100) {
            return new GeMensajeControllerDto(100, "1XX", "Informativos", "Continuar", "El navegador puede continuar realizando su petición (se utiliza para indicar que la primera parte de la petición del navegador se ha recibido correctamente).", "Continuar");
        } else if (status == 101) {
            return new GeMensajeControllerDto(101, "1XX", "Informativos", "Cambiando de protocolos", "El servidor acepta el cambio de protocolo propuesto por el navegador (puede ser por ejemplo un cambio de HTTP 1.0 a HTTP 1.1).", "Cambiando de protocolos");
        } else if (status == 190) {
            return new GeMensajeControllerDto(290, "1XX", "Informativos", "La operación tiene observaciones", "La operacion tiene una validación de negocio que no a cumplido; por lo que no a culminado correctamente!", "");
        } else if (status == 200) {
            return new GeMensajeControllerDto(200,"2XX","Finalización correcta","Correcto","La petición del navegador se ha completado con éxito","Correcto");
        } else if (status == 201) {
            return new GeMensajeControllerDto(201, "2XX", "Finalización correcta", "Creado", "La petición del navegador se ha completado con éxito y como resultado, se ha creado un nuevo recurso (la respuesta incluye la URI de ese recurso).", "Creado");
        } else if (status == 202) {
            return new GeMensajeControllerDto(202, "2XX", "Finalización correcta", "Aceptado", "La petición del navegador se ha aceptado y se está procesando en estos momentos, por lo que todavía no hay una respuesta (se utiliza por ejemplo cuando un proceso realiza una petición muy compleja a un servidor y no quiere estar horas esperando la respuesta).", "Aceptado");
        } else if (status == 203) {
            return new GeMensajeControllerDto(203, "2XX", "Finalización correcta", "Información no autoritativa", "La petición se ha completado con éxito, pero su contenido no se ha obtenido de la fuente originalmente solicitada sino de otro servidor.", "Información no autoritativa");
        } else if (status == 204) {
            return new GeMensajeControllerDto(204, "2XX", "Finalización correcta", "Sin contenido", "La petición se ha completado con éxito pero su respuesta no tiene ningún contenido (la respuesta sí que puede incluir información en sus cabeceras HTTP).", "Sin contenido");
        } else if (status == 205) {
            return new GeMensajeControllerDto(205, "2XX", "Finalización correcta", "Restablecer contenido", "La petición se ha completado con éxito, pero su respuesta no tiene contenidos y además, el navegador tiene que inicializar la página desde la que se realizó la petición (este código es útil por ejemplo para páginas con formularios cuyo contenido debe borrarse después de que el usuario lo envíe).", "Restablecer contenido");
        } else if (status == 206) {
            return new GeMensajeControllerDto(206, "2XX", "Finalización correcta", "Contenido parcial", "La respuesta de esta petición sólo tiene parte de los contenidos, tal y como lo solicitó el propio navegador (se utiliza por ejemplo cuando se descarga un archivo muy grande en varias partes para acelerar la descarga).", "Contenido parcial");
        } else if (status == 207) {
            return new GeMensajeControllerDto(207, "2XX", "Finalización correcta", "Multi-Status (WebDAV)", "La respuesta consiste en un archivo XML que contiene en su interior varias respuestas diferentes (el número depende de las peticiones realizadas previamente por el navegador).", "Multi-Status (WebDAV)");
        } else if (status == 208) {
            return new GeMensajeControllerDto(208, "2XX", "Finalización correcta", "Already Reported (WebDAV)", "El listado de elementos DAV ya se notificó previamente, por lo que no se van a volver a listar.", "Already Reported (WebDAV)");
        } else if (status == 290) {
            return new GeMensajeControllerDto(290, "2XX", "Finalización correcta", "La operación tiene observaciones", "La operacion tiene una validación de negocio que no a cumplido; por lo que no a culminado correctamente!", "");
        } else if (status == 291) {
            return new GeMensajeControllerDto(291, "2XX", "Finalización correcta", "La operación tiene observaciones", "La operacion tiene una validación de negocio que no a cumplido; por lo que no a culminado correctamente!", "");
        } else if (status == 292) {
            return new GeMensajeControllerDto(292, "2XX", "Finalización correcta", "La operación tiene observaciones", "La operacion tiene una validación de negocio que no a cumplido; por lo que no a culminado correctamente!", "");
        } else if (status == 300) {
            return new GeMensajeControllerDto(300, "3XX", "Redireccionamiento", "Opciones múltiples", "Existe más de una variante para el recurso solicitado por el navegador (por ejemplo si la petición se corresponde con más de un archivo).", "Opciones múltiples");
        } else if (status == 301) {
            return new GeMensajeControllerDto(301, "3XX", "Redireccionamiento", "Movido definitivamente", "El recurso solicitado por el navegador se encuentra en otro lugar y este cambio es permanente. El navegador es redirigido automáticamente a la nueva localización de ese recurso (este código es muy importante para tareas relacionadas con el SEO de los sitios web).", "Movido definitivamente");
        } else if (status == 302) {
            return new GeMensajeControllerDto(302, "3XX", "Redireccionamiento", "Encontrados", "El recurso solicitado por el navegador se encuentra en otro lugar, aunque sólo por tiempo limitado. El navegador es redirigido automáticamente a la nueva localización de ese recurso.", "Encontrados");
        } else if (status == 303) {
            return new GeMensajeControllerDto(303, "3XX", "Redireccionamiento", "Ver otro", "El recurso solicitado por el navegador se encuentra en otro lugar. El servidor no redirige automáticamente al navegador, pero le indica la nueva URI en la que se puede obtener el recurso.", "Ver otro");
        } else if (status == 304) {
            return new GeMensajeControllerDto(304, "3XX", "Redireccionamiento", "No modificado", "Cuando el navegador pregunta si un recurso ha cambiado desde la última vez que se solicitó, el servidor responde con este código cuando el recurso no ha cambiado.", "No modificado");
        } else if (status == 305) {
            return new GeMensajeControllerDto(305, "3XX", "Redireccionamiento", "Usar proxy", "El recurso solicitado por el navegador debe obtenerse a través del proxy cuya dirección se indica en la cabecera Location de esta misma respuesta.", "Usar proxy");
        } else if (status == 306) {
            return new GeMensajeControllerDto(306, "3XX", "Redireccionamiento", "cambiar proxy", "Este código se utilizaba en las versiones antiguas de HTTP pero ya no se usa (aunque está reservado para usos futuros).", "cambiar proxy");
        } else if (status == 307) {
            return new GeMensajeControllerDto(307, "3XX", "Redireccionamiento", "Redireccionar temporalmente", "El recurso solicitado por el navegador se puede obtener en otro lugar, pero sólo para esta petición. Las próximas peticiones pueden seguir utilizando la localización original del recurso.", "Redireccionar temporalmente");
        } else if (status == 308) {
            return new GeMensajeControllerDto(308, "3XX", "Redireccionamiento", "Redireccionar permanente", "El recurso solicitado por el navegador se encuentra en otro lugar y este cambio es permanente. A diferencia del código 301, no se permite cambiar el método HTTP para la nueva petición (así por ejemplo, si envías un formulario a un recurso que ha cambiado de lugar, todo seguirá funcionando bien).", "Redireccionar permanente");
        } else if (status == 400) {
            return new GeMensajeControllerDto(400, "4XX", "Errores de cliente", "Solicitud incorrecta", "El servidor no es capaz de entender la petición del navegador porque su sintaxis no es correcta.", "La petición fue incorrecta; comuniquese con el Área de Sistemas.");
        } else if (status == 401) {
            return new GeMensajeControllerDto(401, "4XX", "Errores de cliente", "No autorizado", "El recurso solicitado por el navegador requiere de autenticación. La respuesta incluye una cabecera de tipo WWW-Authenticate para que el navegador pueda iniciar el proceso de autenticación.", "El sistema necesita que se autentique para realizar esta operación.");
        } else if (status == 402) {
            return new GeMensajeControllerDto(402, "4XX", "Errores de cliente", "Se requiere pago", "Este código está reservado para usos futuros.", "El sistema requiere un pago para utilizar esta opcion.");
        } else if (status == 403) {
            return new GeMensajeControllerDto(403, "4XX", "Errores de cliente", "Prohibido", "La petición del navegador es correcta, pero el servidor no puede responder con el recurso solicitado porque se ha denegado el acceso.", "Esta autenticado pero su usuario no tiene acceso a esta opcion.");
        } else if (status == 404) {
            return new GeMensajeControllerDto(404, "4XX", "Errores de cliente", "No encontrado", "El servidor no puede encontrar el recurso solicitado por el navegador y no es posible determinar si esta ausencia es temporal o permanente.", "La opción no está disponible; comuniquese con el Área de Sistemas");
        } else if (status == 405) {
            return new GeMensajeControllerDto(405, "4XX", "Errores de cliente", "Método no permitido", "El navegador ha utilizado un método (GET, POST, etc.) no permitido por el servidor para obtener ese recurso.", "El metodo no está permitido; comuniquese con el Área de Sistemas");
        } else if (status == 406) {
            return new GeMensajeControllerDto(406, "4XX", "Errores de cliente", "No aceptable", "El recurso solicitado tiene un formato que en teoría no es aceptable por el navegador, según los valores que ha indicado en la cabecera Accept de la petición.", "La petición no se acepta; comuniquese con el Área de Sistemas.");
        } else if (status == 407) {
            return new GeMensajeControllerDto(407, "4XX", "Errores de cliente", "Se necesita autorización del Proxy", "Es muy similar al código 401, pero en este caso, el navegador debe autenticarse primero con un proxy.", "Se necesita autorización de un proxy; comuniquese con el Área de Sistemas");
        } else if (status == 408) {
            return new GeMensajeControllerDto(408, "4XX", "Errores de cliente", "Tiempo de espera de la petición", "El navegador ha tardado demasiado tiempo en realizar su petición y el servidor ya no espera esa petición. No obstante, el navegador puede realizar nuevas peticiones cuando quiera.", "El recurso está ocupado intente nuevamente en unos minutos");
        } else if (status == 409) {
            return new GeMensajeControllerDto(409, "4XX", "Errores de cliente", "Conflicto", "A petición del navegador no se ha podido completar porque se ha producido un conflicto con el recurso solicitado. El caso más habitual es el de las peticiones de tipo PUT que intentan modificar un recurso que a su vez ya ha sido modificado por otro lado.", "El recurso está ocupado intente nuevamente en unos minutos");
        } else if (status == 410) {
            return new GeMensajeControllerDto(410, "4XX", "Errores de cliente", "Desaparecido", "No es posible encontrar el recurso solicitado por el navegador y esta ausencia se considera permanente. Si existe alguna posibilidad de que el recurso vuelva a estar disponible, se debe utilizar el código 404.", "La opción no está disponible; comuniquese con el Área de Sistemas");
        } else if (status == 411) {
            return new GeMensajeControllerDto(411, "4XX", "Errores de cliente", "Longitud requerida", "El servidor rechaza la petición del navegador porque no incluye la cabecera Content-Length adecuada.", "No se ha incluido la cabecera; comuniquese con el Área de Sistemas");
        } else if (status == 412) {
            return new GeMensajeControllerDto(412, "4XX", "Errores de cliente", "Error en la condición previa", "El servidor no es capaz de cumplir con algunas de las condiciones impuestas por el navegador en su petición.", "El servidor requiere cumplir condiciones del navegador; comuniquese con el Área de Sistemas");
        } else if (status == 413) {
            return new GeMensajeControllerDto(413, "4XX", "Errores de cliente", "Entidad de solicitud demasiado grande", "La petición del navegador es demasiado grande y por ese motivo el servidor no la procesa.", "La solicitud es demasida grande; comuniquese con el Área de Sistemas");
        } else if (status == 414) {
            return new GeMensajeControllerDto(414, "4XX", "Errores de cliente", "Identificador URI de la solicitud demasiado largo", "La URI de la petición del navegador es demasiado grande y por ese motivo el servidor no la procesa (esta condición se produce en muy raras ocasiones y casi siempre porque el navegador envía como GET una petición que debería ser POST).", "La URL es demasiada larga; comuniquese con el Área de Sistemas");
        } else if (status == 415) {
            return new GeMensajeControllerDto(415, "4XX", "Errores de cliente", "Tipo de medio no compatible", "La petición del navegador tiene un formato que no entiende el servidor y por eso no se procesa.", "La petición no es compatible; comuniquese con el Área de Sistemas");
        } else if (status == 416) {
            return new GeMensajeControllerDto(416, "4XX", "Errores de cliente", "El intervalo pedido no es adecuado", "El navegador ha solicitado una porción inexistente de un recurso. Este error se produce cuando el navegador descarga por partes un archivo muy grande y calcula mal el tamaño de algún trozo.", "Error al obtener un archivo grande; comuniquese con el Área de Sistemas");
        } else if (status == 417) {
            return new GeMensajeControllerDto(417, "4XX", "Errores de cliente", "Error en las expectativas", "La petición del navegador no se procesa porque el servidor no es capaz de cumplir con los requerimientos de la cabecera Expect de la petición.", "Error en las espectativas; comuniquese con el Área de Sistemas");
        } else if (status == 491) {
            return new GeMensajeControllerDto(491, "4XX", "Errores de cliente", "Error en las expectativas", "La petición del navegador no se procesa porque el servidor no es capaz de cumplir con los requerimientos de la cabecera Expect de la petición.", "Error en las espectativas; comuniquese con el Área de Sistemas");
        } else if (status == 500) {
            return new GeMensajeControllerDto(500, "5XX", "Errores de servidor", "Error interno del servidor", "La solicitud del navegador no se ha podido completar porque se ha producido un error inesperado en el servidor.", "Error interno del servidor; comuniquese con el Área de Sistemas");
        } else if (status == 501) {
            return new GeMensajeControllerDto(501, "5XX", "Errores de servidor", "No implementado", "El servidor no soporta alguna funcionalidad necesaria para responder a la solicitud del navegador (como por ejemplo el método utilizado para la petición).", "Error en el servidor por funcionalidad no soportada; comuniquese con el Área de Sistemas");
        } else if (status == 502) {
            return new GeMensajeControllerDto(502, "5XX", "Errores de servidor", "Puerta de enlace no válida", "El servidor está actuando de proxy o gateway y ha recibido una respuesta inválida del otro servidor, por lo que no puede responder adecuadamente a la petición del navegador.", "La puerta de enlace no es válida; comuniquese con el Área de Sistemas");
        } else if (status == 503) {
            return new GeMensajeControllerDto(503, "5XX", "Errores de servidor", "Servicio no disponible", "El servidor no puede responder a la petición del navegador porque está congestionado o está realizando tareas de mantenimiento.", "Servicio no disponible; comuniquese con el Área de Sistemas");
        } else if (status == 504) {
            return new GeMensajeControllerDto(504, "5XX", "Errores de servidor", "Tiempo de espera agotado para la puerta de enlace", "El servidor está actuando de proxy o gateway y no ha recibido a tiempo una respuesta del otro servidor, por lo que no puede responder adecuadamente a la petición del navegador.", "Tiempo agotado; comuniquese con el Área de Sistemas");
        } else if (status == 505) {
            return new GeMensajeControllerDto(505, "5XX", "Errores de servidor", "Versión de HTTP no compatible", "El servidor no soporta o no quiere soportar la versión del protocolo HTTP utilizada en la petición del navegador.", "La versión de HTTP no es compatible; comuniquese con el Área de Sistemas");
        }else if (status == 599) {
            return new GeMensajeControllerDto(599, "5XX", "Errores de servidor", "Mensaje Personalizado", "El servidor ha generado una excepción en la capa del Controlador, por favor ver el detalle!", "El sistema dió un error que el usuario no puede interpretar; por favor comuniquese con el Área de Sistemas");
        }else if (status == 598) {
            return new GeMensajeControllerDto(598, "5XX", "Errores de servidor", "Mensaje Personalizado", "El servidor ha generado una excepción en la capa de Servicios; por favor ver detalle!", "El sistema dió un error que el usuario no puede interpretar; por favor comuniquese con el Área de Sistemas");
        }else if (status == 590) {
            return new GeMensajeControllerDto(590, "5XX", "Errores de servidor", "Observación de Negocio", "La operación realizada no ha cumplido una validación de negocio requerida!", "");
        }

        return new GeMensajeControllerDto(590, "5XX", "Errores de servidor", "Mensaje Personalizado", "El servidor ha generado un error que no se a podido identificar; por favor ver detalle!", "El sistema dió un error que el usuario no puede interpretar; por favor comuniquese con el Área de Sistemas");


    }
}
