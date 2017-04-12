/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

/**
 * @author Administrador
 */
public class UtlString {

    public static String rPad(String s, int total, char c) {
        int longitud = s.length();
        if (longitud < total) {
            int falta = total - longitud;
            for (int i = 0; i < falta; i++) {
                s += c;
            }
        }
        return s;
    }

    /**
     * Concatena caracteres por la izquierda
     */
    public static String lPad(String s, int total, char c) {
        int longitud = s.length();
        String cadena1 = "";
        if (longitud < total) {
            int falta = total - longitud;
            for (int i = 0; i < falta; i++) {
                cadena1 += c;
            }
        }
        cadena1 += s;
        return cadena1;
    }

    /**
     * Elimina caracteres encontrados
     */
    public static String eliminarCaracter(String cad, char car) {
        String s = "";
        char c;
        for (int i = 0; i < cad.length(); i++) {
            c = cad.charAt(i);
            if (c != car) {
                s += c;
            }
        }
        return s;
    }

    /**
     * Retorna una linea de caracteres de longitud especificada.
     */
    public static String formarCadena(char c, int n) {
        char x[] = new char[n];
        if (n <= 0) {
            return "";
        }
        int j = 0;
        while (j < n) {
            x[j] = c;
            j++;
        }
        String s = new String(x);
        return s;
    }
}
