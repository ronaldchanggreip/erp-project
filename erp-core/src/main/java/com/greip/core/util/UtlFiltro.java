package com.greip.core.util;

import com.greip.core.constant.ConstantesFiltro;
import com.greip.core.dto.FiltroDetaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.dto.FiltroOrderDto;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HvivesO on 05/01/2017.
 */
public class UtlFiltro {

    public static Logger log = Logger.getLogger(UtlFiltro.class);

    public static FiltroTransDto generarCondicion(FiltroDto filtroDto, String alias){
        FiltroTransDto filtroTransDto = new FiltroTransDto();
        String condicion = "";
        List<Object> params = new ArrayList<>();
        String order = "";

        try {
            if(filtroDto.getFiltros()!=null && !filtroDto.getFiltros().isEmpty()){
                if(!filtroDto.isFplantilla()) {
                    FiltroDetaDto deta;
                    for (int i = 0; i < filtroDto.getFiltros().size(); i++) {
                        deta = filtroDto.getFiltros().get(i);
                        if (i == 0)
                            condicion += obtenerComparacion(alias, deta);
                        else
                            condicion += " and " + obtenerComparacion(alias, deta);
                        params.add(obtenerPorTipoDato(deta));
                    }
                }else{
                    String plantilla = filtroDto.getPlantilla();
                    String expresion;
                    FiltroDetaDto deta;
                    for (int i = 0; i < filtroDto.getFiltros().size(); i++) {
                        deta = filtroDto.getFiltros().get(i);
                        expresion = obtenerComparacion(alias, deta);
                        plantilla = reemplazarTagPlantilla(plantilla, expresion, deta.getCampo());
                        params.add(obtenerPorTipoDato(deta));
                    }
                    condicion = plantilla;
                }
            }

            if(filtroDto.getOrders()!=null && !filtroDto.getOrders().isEmpty()){
                FiltroOrderDto ord;
                for (int i = 0; i < filtroDto.getOrders().size(); i++){
                    ord = filtroDto.getOrders().get(i);
                    if(i == 0)
                        order += " " + alias + "." + ord.getCampo() + " " + ord.getDireccion();
                    else
                        order += " , " + alias + "."  + ord.getCampo() + " " + ord.getDireccion();
                }
            }
        }catch (Exception ex){
            log.error(ex);
        }

        filtroTransDto.setCondicion(condicion);
        filtroTransDto.setParams(params);
        filtroTransDto.setOrder(order);
        return filtroTransDto;
    }

    private static String reemplazarTagPlantilla(String plantilla, String expresion, String campo){
        return plantilla.replace("{" + campo + "}", expresion);
    }

    private static String obtenerComparacion(String alias, FiltroDetaDto deta){
        String valor = deta.getValor();
        String campo = deta.getCampo();
        String operador = deta.getOperador();
        switch (operador){
            case ConstantesFiltro.Operadores.EQ:
                return alias + "." + campo + " " + ConstantesFiltro.Operadores.EQ + " ?";
            case ConstantesFiltro.Operadores.NEQ:
                return alias + "." + campo + " " + ConstantesFiltro.Operadores.NEQ + " ?";
            case ConstantesFiltro.Operadores.MAY:
                return alias + "." + campo + " " + ConstantesFiltro.Operadores.MAY + " ?";
            case ConstantesFiltro.Operadores.MEN:
                return alias + "." + campo + " " + ConstantesFiltro.Operadores.MEN + " ?";
            case ConstantesFiltro.Operadores.MAYEQ:
                return alias + "." + campo + " " + ConstantesFiltro.Operadores.MAYEQ + " ?";
            case ConstantesFiltro.Operadores.MENEQ:
                return alias + "." + campo + " " + ConstantesFiltro.Operadores.MENEQ + " ?";
            case ConstantesFiltro.Operadores.LIKE:
                return "upper( " + alias + "." + campo + ") " + ConstantesFiltro.Operadores.LIKE + " ?";
            default:
                return null;
        }
    }

    private static Object obtenerPorTipoDato(FiltroDetaDto deta){
        String tipo = deta.getTipoDato();
        String valor = deta.getValor();
        String operador = deta.getOperador();

        SimpleDateFormat formatter = new SimpleDateFormat(UtlDate.FORMATO_FECHA_LATINO);
        try {
            switch (tipo) {
                case ConstantesFiltro.TipoDato.T_ENTERO:
                    return new BigInteger(valor);
                case ConstantesFiltro.TipoDato.T_INTEGER:
                    return new Integer(valor);
                case ConstantesFiltro.TipoDato.T_LONG:
                    return new Long(valor);
                case ConstantesFiltro.TipoDato.T_CARACTER:
                    if(operador.equals(ConstantesFiltro.Operadores.LIKE))
                        return valor.toUpperCase();
                    else
                        return valor;
                case ConstantesFiltro.TipoDato.T_REAL:
                    return new BigDecimal(valor);
                case ConstantesFiltro.TipoDato.T_BOOL:
                    return new Boolean(valor);
                case ConstantesFiltro.TipoDato.T_DATE:
                    Date fecha = formatter.parse(valor);
                    return fecha;
                default:
                    return null;
            }
        }catch(Exception e){
            log.error(e);
        }
        return null;
    }
}
