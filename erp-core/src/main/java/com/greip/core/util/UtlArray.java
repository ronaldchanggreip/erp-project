/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

import java.util.List;

/**
 * @author Administrador
 */
public class UtlArray {

    public static String concatenarLista(List<String> lst) {
        String cadena = "";
        for (String s : lst) {
            cadena += s;
        }
        return cadena;
    }
}
