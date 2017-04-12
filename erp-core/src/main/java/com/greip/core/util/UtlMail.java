package com.greip.core.util;

import com.greip.core.dto.GeClaveValorCorreoDto;

/**
 * Created by apple on 26/03/17.
 */
public class UtlMail {

    public static void main (String[] args) {
        String titulo = "EXCHANGE EXPRESS - Nuevas Credenciales";
        String saludo = "Estimado Ronald";
        String parrafoPrincipal = "El sistema le ha generado nuevas credenciales para acceder al sistema";
        String parrafoSecundario = "";
        String parrafoFinal = "Ingresar al Sistema exchange-app";
        String link = "https://exex.pe/exchange-app";
        GeClaveValorCorreoDto cvUsuario = new GeClaveValorCorreoDto("Usuario","20550003521");
        GeClaveValorCorreoDto cvPassword = new GeClaveValorCorreoDto("Password","h%66sk239");



       System.out.println(plantillaHtmlEmail("/u01/projects/exchange/generales/email.css",titulo,saludo,parrafoPrincipal,parrafoSecundario,parrafoFinal,link, cvUsuario, cvPassword));
    }

    /**
     *
     * @param fileStyle Ruta + Nombre del archivo con los estilos del correo
     * @param titulo Titulo del Correo
     * @param saludo Saludo del correo
     * @param parrafoPrincipal Parrafo principal del correo
     * @param parrafoSecundario Parrafor secundario del correo
     * @param parrafoFinal Parrafo Final del correo
     * @param link Link del correo
     * @param lstClaveValor Arreglo de Claves y valores para una lista de informacion
     * @return
     */
    public static String plantillaHtmlEmail(String fileStyle,String titulo, String saludo, String parrafoPrincipal, String parrafoSecundario, String parrafoFinal, String link, GeClaveValorCorreoDto...lstClaveValor) {
        StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        html.append("<head>");
        html.append("<meta name=\"viewport\" content=\"width=device-width\" />");
        html.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        html.append("<title>Mail Exchange Express</title>");
        html.append(cargarStyleHtmlExchange(fileStyle));
        html.append("</head>");

        html.append("<body bgcolor=\"#FFFFFF\">");
        html.append("<table class=\"head-wrap\" bgcolor=\"#673AB7\">");
        html.append("<tr>");
        html.append("<td></td>");
        html.append("<td class=\"header container\" >");
        html.append("<div class=\"content\">");
        html.append("<table bgcolor=\"#673AB7\">");
        html.append("<tr>");
        //html.append("<td><img src=\"http://placehold.it/200x50/\" /></td>");
        html.append("<td align=\"left\"><h6 class=\"collapse\" style=\"color:#FFFFFF\">"+titulo+"</td>");
        html.append("</tr>");
        html.append("</table>");
        html.append("</div>");
        html.append("</td>");
        html.append("<td></td>");
        html.append("</tr>");
        html.append("</table><!-- /HEADER -->");
        html.append("");
        html.append("<table class=\"body-wrap\">");
        html.append("<tr>");
        html.append("<td></td>");
        html.append("<td class=\"container\" bgcolor=\"#FFFFFF\">");
        html.append("<div class=\"content\">");
        //Tabla body
        html.append("<table>");
        html.append("<tr>");
        html.append("<td>");
        html.append("<h3>"+saludo+"</h3>");/*INFO*/
        html.append("<p class=\"lead\">"+parrafoPrincipal+"</p>");/*INFO*/

        if (parrafoSecundario!=null && !parrafoSecundario.isEmpty()){
            html.append("<p>"+parrafoSecundario+"</p>");
        }

        for (GeClaveValorCorreoDto cv: lstClaveValor) {
            html.append("<table>");
            html.append("<tr>");
            html.append("<td width=\"30%\">");
            html.append(cv.getClave());/*INFO*/
            html.append("<td>");
            html.append("<td width=\"70%\">");
            html.append(cv.getValor());/*INFO*/
            html.append("<td>");
            html.append("</tr>");
            html.append("</table>");
        }

        html.append("<p class=\"callout\">");
        if (link !=null && !link.isEmpty()) {
            html.append(parrafoFinal+ " <a href=\""+link+"\">EXCHANGE-APP! &raquo;</a>");/*INFO*/
        }else {
            html.append(parrafoFinal);
        }
        html.append("</p><!-- /Callout Panel -->\t");
        html.append("");
        html.append("</td>");
        html.append("</tr>");

        html.append("</table>");
        //Tabla body
        html.append("</div><!-- /content -->");

        html.append("</td>");
        html.append("<td></td>");
        html.append("</tr>");
        html.append("</table><!-- /BODY -->");
        html.append("");
        html.append("</body>");
        html.append("</html>");
        return html.toString();
    }

    /**
     *
     * @param estilo Ruta + Nombre del Archivo que contiene los estilos
     * @return StringBuffer con los estilos cargados
     */
    private static StringBuffer cargarStyleHtmlExchange(String estilo){
        StringBuffer htmlStyle = new StringBuffer();
        htmlStyle.append("<style>");
        htmlStyle.append(UtlFile.leerArchivo(estilo));
        htmlStyle.append("</style>");
        return htmlStyle;
    }

}
