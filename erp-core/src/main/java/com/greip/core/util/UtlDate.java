/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Administrador
 */
public class UtlDate {
    public static String FORMATO_FECHA_HORA = "dd-MM-yyyy HH:mm:ss";
    public static String FORMATO_FECHA_HORA_MIN_SEG = "ddMMyyyyHHmmssSS";
    public static String FORMATO_FECHA = "dd-MM-yyyy";
    public static String FORMATO_FECHA_LATINO = "dd/MM/yyyy HH:mm:ss";
    public static String FORMATO_HORA = "HH:mm:ss";
    public static String FORMATO_ANO = "yyyy";

    public static Date convertirTimeZones(Date date, TimeZone fromTZ, TimeZone toTZ) {
        long fromTZDst = 0;
        if (fromTZ.inDaylightTime(date)) {
            fromTZDst = fromTZ.getDSTSavings();
        }

        long fromTZOffset = fromTZ.getRawOffset() + fromTZDst;

        long toTZDst = 0;
        if (toTZ.inDaylightTime(date)) {
            toTZDst = toTZ.getDSTSavings();
        }
        long toTZOffset = toTZ.getRawOffset() + toTZDst;

        return new Date(date.getTime() + (toTZOffset - fromTZOffset));
    }

    public static Date obtenerFechaActualPorTimeZone(String timeZone) {
        Date fecha = null;
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
            fecha = construirFecha(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        } catch (Exception ex) {
            //System.out.println("ERROR: " + ex.getMessage());
        }
        return fecha;
    }

    /**
     * Retorna diferencia en dias entre dos fechas
     *
     * @param antes
     * @param despues
     * @return
     */
    public static int diferenciaDiasFechas(Date antes, Date despues) {
        long diff = despues.getTime() - antes.getTime();
        return Math.round(diff / (1000 * 60 * 60 * 24.0f));
    }

    public static String devolverdiaSemana(Date date) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE");
        String fecha2 = formato.format(date).toUpperCase();
        if (fecha2.equals("MONDAY")) {
            fecha2 = "LU";
        } else if (fecha2.equals("TUESDAY")) {
            fecha2 = "MA";
        } else if (fecha2.equals("WEDNESDAY")) {
            fecha2 = "MI";
        } else if (fecha2.equals("THURSDAY")) {
            fecha2 = "JU";
        } else if (fecha2.equals("FRIDAY")) {
            fecha2 = "VI";
        } else if (fecha2.equals("SATURDAY")) {
            fecha2 = "SA";
        } else {
            fecha2 = "DO";
        }

        return fecha2;
    }

    public static int diferenciaSegundosFechas(Date antes, Date despues) {
        Calendar calAntes = Calendar.getInstance();
        calAntes.setTime(antes);
        Calendar calDespues = Calendar.getInstance();
        calDespues.setTime(despues);

        long milisAntes = calAntes.getTimeInMillis();
        long miliDespues = calDespues.getTimeInMillis();

        long diff = miliDespues - milisAntes;
        int segundos = (int) (diff / 1000);
        return segundos;
    }

    public static int diferenciaMinutosFechas(Date antes, Date despues) {
        long diff = despues.getTime() - antes.getTime();
        return (int) Math.floor(diff / (1000 * 60));
    }

    public static String obtenerFechaActual() { // sacar el fecha de hoy
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        return sdf.format(calendario.getTime());
    }

    public static String obtenerFechaHoraMinutoYSegundoMiliActual() {
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_HORA_MIN_SEG);
        return sdf.format(calendario.getTime());
    }

    public static String obtenerFechaHoraActual() { // sacar el fecha de hoy
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_HORA);
        return sdf.format(calendario.getTime());
    }

    public static Date obtenerFechaActualDate() { // sacar el fecha de hoy
        Calendar calendario = Calendar.getInstance();
        return calendario.getTime();
    }

    public static String formatearFecha(Date fecha, String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(fecha);
    }

    public static String formatearFechaCompare(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
        return sdf.format(fecha);
    }


    public static String obtenerHoraActual() { // sacar la hora actual
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_HORA);
        return sdf.format(calendario.getTime());
    }

    public static String obtenerFechaActualLatino() { // sacar la hora actual
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_LATINO);
        return sdf.format(calendario.getTime());
    }

    public static String obtenerFechaTextoLatino(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_LATINO);
        return sdf.format(fecha.getTime());
    }

    public static int obtenerMesActual() {
        Calendar calendario = Calendar.getInstance();
        calendario = Calendar.getInstance();
        return calendario.get(Calendar.MONTH) + 1;
    }

    public static int obtenerDiaActual() {
        Calendar calendario = Calendar.getInstance();
        calendario = Calendar.getInstance();
        return calendario.get(Calendar.DATE);
    }

    public static int obtenerAnioActual() {
        Calendar calendario = Calendar.getInstance();
        calendario = Calendar.getInstance();
        return calendario.get(Calendar.YEAR);
    }

    public static String obtenerAnioActualString() { // sacar la hora actual
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_LATINO);
        return sdf.format(calendario.getTime());
    }

    public static Date construirFecha(int ano, int mes, int dia) {
        Calendar calendario = Calendar.getInstance();
        calendario.set(ano, mes, dia);
        return calendario.getTime();
    }

    public static Date construirFecha(int ano, int mes, int dia, int hora, int min, int seg) {
        Calendar calendario = Calendar.getInstance();
        calendario.set(ano, mes, dia, hora, min, seg);
        return calendario.getTime();
    }

    public static String agregarDiasFechaActual(int dias) {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DATE, dias);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        return sdf.format(calendario.getTime());
    }

    public static Date agregarDiasFechaInicio(Date fechaInicio, BigDecimal diasAgregar) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaInicio);
        calendario.add(Calendar.DATE, diasAgregar.intValue());
        return calendario.getTime();
    }

    public static Date agregarHorasMinSegFinales(Date fechaInicio) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaInicio);
        calendario.set(Calendar.HOUR, 23);
        calendario.set(Calendar.MINUTE, 59);
        calendario.set(Calendar.SECOND, 59);
        return calendario.getTime();
    }

    public static Date agregarDiasFechaActualDate(int dias) {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DATE, dias);
        return calendario.getTime();
    }

    public static Date agregarDiasFechaDate(Date fecha, int dias) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.add(Calendar.DATE, dias);
        return calendario.getTime();
    }

    public static Date agregarMesesFecha(Date date, int meses) {
        Calendar calendario = Calendar.getInstance();
        Date retorno = null;
        calendario.setTime(date);
        calendario.add(Calendar.MONTH, meses);
        retorno = calendario.getTime();
        return retorno;
    }

    public static Date agregarAniosFecha(Date date, int anios) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        calendario.add(Calendar.YEAR, anios);
        return calendario.getTime();
    }

    public static Boolean validarRangoFechas(Date fechaInicio, Date fechaFin) {
        Calendar calendarioInicio = Calendar.getInstance();
        calendarioInicio.setTime(fechaInicio);
        calendarioInicio.getTimeInMillis();
        Calendar calendarioFin = Calendar.getInstance();
        calendarioFin.setTime(fechaFin);
        if (calendarioInicio.compareTo(calendarioFin) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertirMinutosAHMS(BigDecimal minutosTotal) {
        int segundos = minutosTotal.multiply(new BigDecimal("60")).intValue();
        int horas = segundos / 3600;
        segundos = segundos % 3600;
        int minutos = segundos / 60;
        segundos = segundos % 60;

        return horas + "h " + minutos + "m " + segundos + "s";
    }

    public static void main(String[] args){
        System.out.println(UtlDate.obtenerFechaActualDate());
    }
}
