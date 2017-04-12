/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrador
 */
public class UtlValidation {

    public static final String ES_FORMATO_EMAIL = "[a-zA-Z0-9_]+[.[a-zA-Z0-9]+]*@[[a-zA-Z0-9_]+.[a-zA-Z0-9]+]+";
    public static final String ES_FORMATO_ENTERO = "[0-9]+";
    public static final String ES_FORMATO_REAL = "[0-9]+|[0-9]+[.][0-9]+";
    public static final String ES_FORMATO_ENTERO_SIGNO = "[+-]*[0-9]+";
    public static final String ES_FORMATO_REAL_SIGNO = "[+-]*[0-9]+|[+-]*[0-9]+[.][0-9]+";
    public static final String ES_FORMATO_IP = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";


    public static boolean esEntero(String s) {
        Pattern p = Pattern.compile(ES_FORMATO_ENTERO);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /*
     * Devuelve true si la cadena tiene silo un numero entero con o sin
     * signo, de lo contrario devuelve false.
     */
    public static boolean esEnteroConSigno(String s) {
        Pattern p = Pattern.compile(ES_FORMATO_ENTERO);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /*
     * Devuelve true si la cadena tiene un numero entero o real con o sin
     * signo, de lo contrario devuelve false.
     */
    public static boolean esReal(String s) {
        Pattern p = Pattern.compile(ES_FORMATO_REAL);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /*
     * Devuelve true si la cadena tiene un numero entero o real, de lo
     * contrario devuelve false.
     */
    public static boolean esRealConSigno(String s) {
        Pattern p = Pattern.compile(ES_FORMATO_REAL_SIGNO);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /*
     * Devuelve true si la cadena representa un email correcto, de lo
     * contrario devuelve false.
     */
    public static boolean esEmail(String s) {
        Pattern p = Pattern.compile(ES_FORMATO_EMAIL);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean esIP(String s) {
        Pattern p = Pattern.compile(ES_FORMATO_IP);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean esFormatoFecha(String fecha, String separador) {
        String campo = fecha;

        if (campo.length() > 0) {
            if (campo.length() != 10) {

                return false;
            }
            // saca de la fecha dia, mes y a�o
            String[] fech1 = campo.split("[" + separador + "]");
            // comprueba que haya introducido el formato dd-mm-yyyy, con el
            // separador
            // especificado
            if (fech1.length < 3) {

                return false;
            }
            int dia = new Integer(fech1[0]).intValue();
            int mes = new Integer(fech1[1]).intValue();
            int anio = new Integer(fech1[2]).intValue();
            // el mes debe estar entre 1 y 12
            if (mes < 1 || mes > 12) {

                return false;
            }
            // comprueba que el anio este entre 1900 y 2099
            if (anio < 1000 || anio > 9999) {

                return false;
            }
            // comprueba el numero de dias dependiendo del mes
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8
                    || mes == 10 || mes == 12) {
                if (dia < 1 || dia > 31) {

                    return false;
                }
            }
            if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                if (dia < 1 || dia > 30) {

                    return false;
                }
            }
            if (mes == 2) {
                if (anio % 4 == 0) {
                    if (dia > 29) {

                        return false;
                    }
                } else if (anio % 100 == 0 && anio % 400 > 0) {
                    if (dia > 29) {

                        return false;
                    }
                } else {
                    if (dia > 28) {

                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;

    }
}
