/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Administrador
 */
public class UtlCaracter {

    public static String asciiToHex(String ascii) {
        StringBuilder hex = new StringBuilder();

        for (int i = 0; i < ascii.length(); i++) {
            hex.append(Integer.toHexString(ascii.charAt(i)));
        }
        return hex.toString();
    }

    public static String hexToAscii(String s) {
        int n = s.length();
        StringBuilder sb = new StringBuilder(n / 2);
        for (int i = 0; i < n; i += 2) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);
            sb.append((char) ((hexToInt(a) << 4) | hexToInt(b)));
        }
        return sb.toString();
//			  StringBuilder sb = new StringBuilder();
//	          StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
//	          for( int i=0; i<hex.length(); i+=2 ){
//	      
//	              //grab the hex in pairs
//	              String output = hex.substring(i, (i + 2));
//	              System.out.println("Decimal 1: " + output);
//	              //convert hex to decimal
//	              int decimal = Integer.parseInt(output, 16);
//	              System.out.println("Decimal : " + decimal);
//	              //convert the decimal to character
//	              sb.append((char)decimal);
//	      
//	              temp.append(decimal);
//	          }
//	          System.out.println("Decimal : " + temp.toString());
//	      
//	          return sb.toString();
    }

    private static int hexToInt(char ch) {
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        throw new IllegalArgumentException(String.valueOf(ch));
    }

    public static int hexToDecimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    public static String obtenerXORHexString(List<String> lenData) {
        String resultado = "";
        int indexCut = 0;
        while (indexCut < lenData.size()) {
            if (indexCut == 0) {
                resultado = xorHex(UtlArray.concatenarLista(lenData.subList(indexCut, indexCut + 1)), UtlArray.concatenarLista(lenData.subList(indexCut + 1, indexCut + 2)));
//				  System.out.println("este : " + Array.concatenarLista(lenData.subList(indexCut,indexCut + 1)) +" con este : "+Array.concatenarLista(lenData.subList(indexCut + 1,indexCut + 2)));
                indexCut += 2;
            } else {
//				  System.out.println("este : " + resultado + " con este : " + Array.concatenarLista(lenData.subList(indexCut,indexCut + 1)));
                resultado = xorHex(resultado, UtlArray.concatenarLista(lenData.subList(indexCut, indexCut + 1)));

                indexCut += 1;
            }
        }
        return resultado;
    }

    private static String xorHex(String a, String b) {
        if (a.length() == 1) {
            a = "0" + a;
        }
        if (b.length() == 1) {
            b = "0" + b;
        }
        // TODO: Validation
        char[] chars = new char[a.length()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
        }
        return new String(chars);
    }

    private static int fromHex(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        throw new IllegalArgumentException();
    }

    private static char toHex(int nybble) {
        if (nybble < 0 || nybble > 15) {
            throw new IllegalArgumentException();
        }
        return "0123456789ABCDEF".charAt(nybble);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public static String generarHexStringConLongitud(String cadena, int espacios) {
        String hexString = "";
        int longitud = cadena.length();
        if (longitud < espacios) {
            int faltante = espacios - longitud;
            for (int i = 0; i < faltante; i++) {
                hexString += "0";
            }
        }
        hexString += cadena;
        return hexString;
    }

    public static List<String> llenarItemsConCero(List<String> lst) {
        List<String> lstNew = new ArrayList<String>();
        String cadena = "";
        for (String s : lst) {
            cadena = s;
            if (s.length() == 1) {
                cadena = "0" + s;
            }
            lstNew.add(cadena);
        }
        return lstNew;
    }

    public static String concatenarLista(List<String> lst) {
        String cadena = "";
        for (String s : lst) {
            cadena += s;
        }
        return cadena;
    }

    public static String generarNombreArchivo(BigInteger entidad, BigInteger registro, BigInteger idArchivo, String nombre){
        String separadorArchivoAbrir = "[";
        String separadorArchivoCerrar = "]";

        StringBuilder sb = new StringBuilder("");
        sb.append(separadorArchivoAbrir);
        sb.append(entidad.toString());
        sb.append(separadorArchivoCerrar);
        sb.append("-");
        sb.append(separadorArchivoAbrir);
        sb.append(registro.toString());
        sb.append(separadorArchivoCerrar);
        if(idArchivo!=null){
            sb.append("-");
            sb.append(separadorArchivoAbrir);
            sb.append(idArchivo.toString());
            sb.append(separadorArchivoCerrar);
        }
        sb.append("-");
        sb.append(nombre);
        return sb.toString();
    }

    public static String getCadenaAlfanumAleatoria (int longitud){
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return cadenaAleatoria;
    }
}
