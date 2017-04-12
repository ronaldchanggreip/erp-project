/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greip.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author Administrador
 */
public class UtlNumber {

    public static DecimalFormat dfDouble, dfInteger;

    private static final String[] UNIDADES = {"", "UN ", "DOS ", "TRES ",
            "CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE ", "DIEZ ",
            "ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS",
            "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE"};
    private static final String[] DECENAS = {"VENTI", "TREINTA ", "CUARENTA ",
            "CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA ",
            "CIEN "};
    private static final String[] CENTENAS = {"CIENTO ", "DOSCIENTOS ",
            "TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ",
            "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS "};

    public static boolean esNumero(String cadena) {
        try {
            new BigDecimal(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static double round(double val, int places) {
        long factor = (long) Math.pow(10, places);

        // Shift the decimal the correct number of places
        // to the right.
        val = val * factor;

        // Round to the nearest integer.
        long tmp = Math.round(val);

        // Shift the decimal the correct number of places
        // back to the left.
        return (double) tmp / factor;
    }

    public static BigDecimal round(BigDecimal val, int places) {
        MathContext mc = new MathContext(places + 1, RoundingMode.HALF_UP);
        return val.round(mc);
    }

    public static Float round(Float val, int places) {
        double d = round(val.doubleValue(), places);
        return new Float(d);
        // return new Float(tmp / factor);
    }

    public static String redondear(double numero) { // redonear un numero real a
        // 2 digitos decimales
        return dfDouble.format(numero);
    }

    public static Double redondear(Double numero) { // redonear un numero real a
        // 2 digitos decimales
        return Double.parseDouble(dfDouble.format(numero));
    }

    public static String redondear(int numero) {
        return dfInteger.format(numero);
    }

    public static String redondear(long numero) {
        return dfInteger.format(numero);
    }

    public static String formatearDouble(String patron, double dato) {
        DecimalFormat formato = new DecimalFormat(patron);
        String salida = formato.format(dato);
        return salida;
    }

    public static BigDecimal valorEnPorcentaje(BigDecimal valor, int precision) {
        BigDecimal v = valor.divide(BigDecimal.valueOf(100), precision, RoundingMode.HALF_UP);
        return v;
    }

    public static BigDecimal dividir(BigDecimal dividendo, BigDecimal divisor, int precision) {
        BigDecimal v = dividendo.divide(divisor, precision, RoundingMode.HALF_UP);
        return v;
    }

    public static BigDecimal dividir(BigDecimal dividendo, Long divisor, int precision) {
        BigDecimal v = dividendo.divide(BigDecimal.valueOf(divisor), precision, RoundingMode.HALF_UP);
        return v;
    }

    public static BigDecimal dividir(Long dividendo, Long divisor, int precision) {
        BigDecimal v = BigDecimal.valueOf(dividendo).divide(BigDecimal.valueOf(divisor), precision, RoundingMode.HALF_UP);
        return v;
    }

    public static BigDecimal valorEnPorcentajeOferta(BigDecimal valor, int precision) {
        BigDecimal v = valor.divide(BigDecimal.valueOf(100), precision, RoundingMode.HALF_UP);
        return v;
    }

    public static BigDecimal prorrateo(BigDecimal aProrratear, BigDecimal dividendo, BigDecimal divisor, int precision) {
        BigDecimal v = aProrratear.multiply(dividir(dividendo, divisor, precision));
        return v;
    }

    public static BigDecimal porcentaje(BigDecimal parcial, BigDecimal total, int precision) {
        BigDecimal v = parcial.divide(total, precision, RoundingMode.HALF_UP);
        return v.multiply(BigDecimal.valueOf(100));
    }

    public static BigDecimal porcentaje(BigDecimal parcial, BigDecimal total) {
        int precision = 2;
        BigDecimal v = parcial.divide(total, precision, RoundingMode.HALF_UP);
        return v.multiply(BigDecimal.valueOf(100));
    }

    //AGREGADO PARA CONVERTIR NUMEROS A LETRAS

    /**
     * /**
     * Metodo que convierte un double a texto
     *
     * @param doubleNumber
     * @param descMoneda
     * @return
     * @throws NumberFormatException
     */
    public static String convertNumberToLetter(double doubleNumber, String descMoneda)
            throws NumberFormatException {
        StringBuilder converted = new StringBuilder();

        // Validamos que sea un numero legal
        if (doubleNumber > 999999999) {
            throw new NumberFormatException(
                    "El numero es mayor de 999'999.999, "
                            + "no es posible convertirlo");
        }

        if (doubleNumber < 0) {
            throw new NumberFormatException("El numero debe ser positivo");
        }

        String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#')
                .split("#");

        // Descompone el trio de millones
        int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                8))
                + String.valueOf(getDigitAt(splitNumber[0], 7))
                + String.valueOf(getDigitAt(splitNumber[0], 6)));
        if (millon == 1) {
            converted.append("UN MILLON ");
        } else if (millon > 1) {
            converted.append(convertNumber(String.valueOf(millon))
                    + "MILLONES ");
        }

        // Descompone el trio de miles
        int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                5))
                + String.valueOf(getDigitAt(splitNumber[0], 4))
                + String.valueOf(getDigitAt(splitNumber[0], 3)));
        if (miles == 1) {
            converted.append("MIL ");
        } else if (miles > 1) {
            converted.append(convertNumber(String.valueOf(miles)) + "MIL ");
        }

        // Descompone el ultimo trio de unidades
        int cientos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[0], 2))
                + String.valueOf(getDigitAt(splitNumber[0], 1))
                + String.valueOf(getDigitAt(splitNumber[0], 0)));
        if (cientos == 1) {
            converted.append("UN");
        }

        if (millon + miles + cientos == 0) {
            converted.append("CERO");
        }
        if (cientos > 1) {
            converted.append(convertNumber(String.valueOf(cientos)));
        }

        converted.append(" " + descMoneda);

        // Descompone los centavos
        int centavos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[1], 2))
                + String.valueOf(getDigitAt(splitNumber[1], 1))
                + String.valueOf(getDigitAt(splitNumber[1], 0)));
        if (centavos == 1) {
            converted.append(" CON UN CENTAVO");
        } else if (centavos > 1) {
            converted.append(" CON " + convertNumber(String.valueOf(centavos))
                    + "CENTAVOS");
        }

        return converted.toString();
    }

    /**
     * Convierte los trios de numeros que componen las unidades, las decenas y
     * las centenas del numero.
     *
     * @param number Numero a convetir en digitos
     * @return Numero convertido en letras
     */
    private static String convertNumber(String number) {

        if (number.length() > 3) {
            throw new NumberFormatException(
                    "La longitud maxima debe ser 3 digitos");
        }

        // Caso especial con el 100
        if (number.equals("100")) {
            return "CIEN";
        }

        StringBuilder output = new StringBuilder();
        if (getDigitAt(number, 2) != 0) {
            output.append(CENTENAS[getDigitAt(number, 2) - 1]);
        }

        int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1))
                + String.valueOf(getDigitAt(number, 0)));

        if (k <= 20) {
            output.append(UNIDADES[k]);
        } else if (k > 30 && getDigitAt(number, 0) != 0) {
            output.append(DECENAS[getDigitAt(number, 1) - 2] + "Y "
                    + UNIDADES[getDigitAt(number, 0)]);
        } else {
            output.append(DECENAS[getDigitAt(number, 1) - 2]
                    + UNIDADES[getDigitAt(number, 0)]);
        }

        return output.toString();
    }

    /**
     * Retorna el digito numerico en la posicion indicada de derecha a izquierda
     *
     * @param origin   Cadena en la cual se busca el digito
     * @param position Posicion de derecha a izquierda a retornar
     * @return Digito ubicado en la posicion indicada
     */
    private static int getDigitAt(String origin, int position) {
        if (origin.length() > position && position >= 0) {
            return origin.charAt(origin.length() - position - 1) - 48;
        }
        return 0;
    }

    public static String convertNumberToLetter(String number) {
        return convertNumberToLetter(Double.parseDouble(number));
    }

    /**
     * Convierte un numero en representacion numerica a uno en
     * representacion de texto. El numero es valido si esta entre 0 y
     * 999'999.999 <p> Creation date 3/05/2006 - 05:37:47 PM
     *
     * @param number Numero a convertir
     * @return Numero convertido a texto
     * @throws NumberFormatException Si el numero esta fuera del rango
     * @since 1.0
     */
    public static String convertNumberToLetter(double number)
            throws NumberFormatException {
        String converted = new String();

        // Validamos que sea un numero legal
        double doubleNumber = number;// Math.round(number);
        if (doubleNumber > 999999999) {
            throw new NumberFormatException(
                    "El numero es mayor de 999'999.999, "
                            + "no es posible convertirlo");
        }

        String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#').split("#");

        // Descompone el trio de millones - !
        int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                8))
                + String.valueOf(getDigitAt(splitNumber[0], 7))
                + String.valueOf(getDigitAt(splitNumber[0], 6)));
        if (millon == 1) {
            converted = "UN MILLON ";
        }
        if (millon > 1) {
            converted = convertNumber(String.valueOf(millon)) + "MILLONES ";
        }

        // Descompone el trio de miles - SGT!
        int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                5))
                + String.valueOf(getDigitAt(splitNumber[0], 4))
                + String.valueOf(getDigitAt(splitNumber[0], 3)));
        if (miles == 1) {
            converted += "MIL ";
        }
        if (miles > 1) {
            converted += convertNumber(String.valueOf(miles)) + "MIL ";
        }

        // Descompone el ultimo trio de unidades - SGT!
        int cientos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[0], 2))
                + String.valueOf(getDigitAt(splitNumber[0], 1))
                + String.valueOf(getDigitAt(splitNumber[0], 0)));
        if (cientos == 1) {
            converted += "UN";
        }

        if (millon + miles + cientos == 0) {
            converted += "CERO";
        }
        if (cientos > 1) {
            converted += convertNumber(String.valueOf(cientos));
        }

        // converted += "PESOS"; //moneda
        // converted += "CON"; //moneda

        // Descompone los centavos - Camilo
        int centavos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[1], 2))
                + String.valueOf(getDigitAt(splitNumber[1], 1))
                + String.valueOf(getDigitAt(splitNumber[1], 0)));
        if (centavos == 0) // converted += " CON 1 CENTAVO";
        {
            converted += " CON 00/100";
        }
        if (centavos >= 1 && centavos < 10) // converted += " CON " + convertNumber(String.valueOf(centavos)) +
        // "CENTAVOS";
        {
            converted += " CON 0" + centavos + "/100";
        }
        if (centavos >= 10) {
            converted += " CON " + centavos + "/100";
        }

        return converted;
    }

    public static String convertNumberToLetter(Double number)
            throws NumberFormatException {
        String converted = new String();

        // Validamos que sea un numero legal
        double doubleNumber = number;// Math.round(number);
        if (doubleNumber > 999999999) {
            throw new NumberFormatException(
                    "El numero es mayor de 999'999.999, "
                            + "no es posible convertirlo");
        }

        String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#').split("#");

        // Descompone el trio de millones - SGT!
        int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                8))
                + String.valueOf(getDigitAt(splitNumber[0], 7))
                + String.valueOf(getDigitAt(splitNumber[0], 6)));
        if (millon == 1) {
            converted = "UN MILLON ";
        }
        if (millon > 1) {
            converted = convertNumber(String.valueOf(millon)) + "MILLONES ";
        }

        // Descompone el trio de miles - SGT!
        int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                5))
                + String.valueOf(getDigitAt(splitNumber[0], 4))
                + String.valueOf(getDigitAt(splitNumber[0], 3)));
        if (miles == 1) {
            converted += "MIL ";
        }
        if (miles > 1) {
            converted += convertNumber(String.valueOf(miles)) + "MIL ";
        }

        // Descompone el ultimo trio de unidades - SGT!
        int cientos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[0], 2))
                + String.valueOf(getDigitAt(splitNumber[0], 1))
                + String.valueOf(getDigitAt(splitNumber[0], 0)));
        if (cientos == 1) {
            converted += "UN";
        }

        if (millon + miles + cientos == 0) {
            converted += "CERO";
        }
        if (cientos > 1) {
            converted += convertNumber(String.valueOf(cientos));
        }

        // converted += "PESOS"; //moneda
        // converted += "CON"; //moneda

        // Descompone los centavos - Camilo
        int centavos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[1], 2))
                + String.valueOf(getDigitAt(splitNumber[1], 1))
                + String.valueOf(getDigitAt(splitNumber[1], 0)));
        if (centavos == 0) // converted += " CON 1 CENTAVO";
        {
            converted += " CON 00/100";
        }
        if (centavos >= 1 && centavos < 10) // converted += " CON " + convertNumber(String.valueOf(centavos)) +
        // "CENTAVOS";
        {
            converted += " CON 0" + centavos + "/100";
        }
        if (centavos >= 10) {
            converted += " CON " + centavos + "/100";
        }

        return converted;
    }

    public static String convertNumberToLetter(Double number, String moneda)
            throws NumberFormatException {
        String converted = new String();

        // Validamos que sea un numero legal
        double doubleNumber = round(number, 2);// Math.round(number);
        if (doubleNumber > 999999999) {
            throw new NumberFormatException(
                    "El numero es mayor de 999'999.999, "
                            + "no es posible convertirlo");
        }

        String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#').split("#");

        // Descompone el trio de millones - SGT!
        int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                8))
                + String.valueOf(getDigitAt(splitNumber[0], 7))
                + String.valueOf(getDigitAt(splitNumber[0], 6)));
        if (millon == 1) {
            converted = "UN MILLON ";
        }
        if (millon > 1) {
            converted = convertNumber(String.valueOf(millon)) + "MILLONES ";
        }

        // Descompone el trio de miles - SGT!
        int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                5))
                + String.valueOf(getDigitAt(splitNumber[0], 4))
                + String.valueOf(getDigitAt(splitNumber[0], 3)));
        if (miles == 1) {
            converted += "MIL ";
        }
        if (miles > 1) {
            converted += convertNumber(String.valueOf(miles)) + "MIL ";
        }

        // Descompone el ultimo trio de unidades - SGT!
        int cientos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[0], 2))
                + String.valueOf(getDigitAt(splitNumber[0], 1))
                + String.valueOf(getDigitAt(splitNumber[0], 0)));
        if (cientos == 1) {
            converted += "UN";
        }

        if (millon + miles + cientos == 0) {
            converted += "CERO";
        }
        if (cientos > 1) {
            converted += convertNumber(String.valueOf(cientos));
        }

        // converted += "PESOS"; //moneda
        // converted += "CON"; //moneda

        // Descompone los centavos - Camilo
        int centavos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[1], 2))
                + String.valueOf(getDigitAt(splitNumber[1], 1))
                + String.valueOf(getDigitAt(splitNumber[1], 0)));

        if (centavos == 0) // converted += " CON 1 CENTAVO";
        {
            converted += " CON 00/100 " + moneda;
        }
        if (centavos >= 1 && centavos < 10) // converted += " CON " + convertNumber(String.valueOf(centavos)) +
        // "CENTAVOS";
        {
            converted += " CON 0" + centavos + "/100 " + moneda;
        }
        if (centavos >= 10) {
            converted += " CON " + centavos + "/100 " + moneda;
        }

        return converted;
    }

    public static String convertNumberToLetter(Float number, String moneda)
            throws NumberFormatException {
        String converted = new String();

        // Validamos que sea un numero legal
        double doubleNumber = round(number, 2).doubleValue();// Math.round(number);
        if (doubleNumber > 999999999) {
            throw new NumberFormatException(
                    "El numero es mayor de 999'999.999, "
                            + "no es posible convertirlo");
        }

        String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#').split("#");

        // Descompone el trio de millones - SGT!
        int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                8))
                + String.valueOf(getDigitAt(splitNumber[0], 7))
                + String.valueOf(getDigitAt(splitNumber[0], 6)));
        if (millon == 1) {
            converted = "UN MILLON ";
        }
        if (millon > 1) {
            converted = convertNumber(String.valueOf(millon)) + "MILLONES ";
        }

        // Descompone el trio de miles - SGT!
        int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                5))
                + String.valueOf(getDigitAt(splitNumber[0], 4))
                + String.valueOf(getDigitAt(splitNumber[0], 3)));
        if (miles == 1) {
            converted += "MIL ";
        }
        if (miles > 1) {
            converted += convertNumber(String.valueOf(miles)) + "MIL ";
        }

        // Descompone el ultimo trio de unidades - SGT!
        int cientos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[0], 2))
                + String.valueOf(getDigitAt(splitNumber[0], 1))
                + String.valueOf(getDigitAt(splitNumber[0], 0)));
        if (cientos == 1) {
            converted += "UN";
        }

        if (millon + miles + cientos == 0) {
            converted += "CERO";
        }
        if (cientos > 1) {
            converted += convertNumber(String.valueOf(cientos));
        }

        // converted += "PESOS"; //moneda
        // converted += "CON"; //moneda

        // Descompone los centavos - Camilo
        int centavos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[1], 2))
                + String.valueOf(getDigitAt(splitNumber[1], 1))
                + String.valueOf(getDigitAt(splitNumber[1], 0)));

        int parteEntera = Integer.parseInt(splitNumber[0]);
        double parteDecimal = doubleNumber % parteEntera;
        parteDecimal = round(parteDecimal, 2);

        if (parteDecimal != 0.29) {
            centavos = (int) (parteDecimal * 100);
        } else {
            centavos = 29;
        }
        if (centavos == 0) // converted += " CON 1 CENTAVO";
        {
            converted += " CON 00/100 " + moneda;
        }
        if (centavos >= 1 && centavos < 10) // converted += " CON " + convertNumber(String.valueOf(centavos)) +
        // "CENTAVOS";
        {
            converted += " CON 0" + centavos + "/100 " + moneda;
        }
        if (centavos >= 10) {
            converted += " CON " + centavos + "/100 " + moneda;
        }

        return converted;
    }


    public static int numCifras(int x) {
        int total = 0;
        while (x != 0) {
            x = x / 10;
            total += 1; // incrementamos el contador }
        }
        return total;
    }
}
