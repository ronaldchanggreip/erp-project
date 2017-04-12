package com.greip.core.constant;

import java.math.BigInteger;

/**
 * Created by HvivesO on 10/01/2017.
 */
public class ConstantesCore {
    public static class ValoresPorDefecto{
        public static final int ACCION_NUEVO = 1;
        public static final int ACCION_EDITAR = 2;

        public static final String MAIL_USER = "administracion@greip.com.pe";
        public static final String MAIL_PWD = "Greip2017";



        public static final String ESTSOL_COD_PENDIENTE = "P";
        public static final String ESTSOL_COD_OBSERVADA = "O";
        public static final String ESTSOL_COD_ANULADA = "A";
        public static final String ESTSOL_COD_ABORTADA = "B";
        public static final String ESTSOL_COD_REVISADA = "R";
        public static final String ESTSOL_COD_EJECUTADA = "E";
        public static final String ESTSOL_COD_VALIDADA= "V";

        public static final String ESTSOL_DESC_PENDIENTE = "Pendiente";
        public static final String ESTSOL_DESC_OBSERVADA = "Observada";
        public static final String ESTSOL_DESC_ANULADA = "Anulada";
        public static final String ESTSOL_DESC_ABORTADA = "Abortada";
        public static final String ESTSOL_DESC_REVISADA = "Revisada";
        public static final String ESTSOL_DESC_EJECUTADA = "Finalizada";
        public static final String ESTSOL_DESC_VALIDADA= "Validada";

        public static final String ETAPASOL_COD_REGISTRO = "R";
        public static final String ETAPASOL_COD_REVISION = "E";
        public static final String ETAPASOL_COD_VALIDACION = "V";
        public static final String ETAPASOL_COD_EJECUCION = "J";

        public static final String ETAPASOL_DESC_REGISTRO = "En Registro";
        public static final String ETAPASOL_DESC_REVISION = "En Revisión";
        public static final String ETAPASOL_DESC_VALIDACION = "En Validación";
        public static final String ETAPASOL_DESC_EJECUCION = "En Ejecución";

        public static final String MOVIM_BITACORA_COD_ALTA = "A";
        public static final String MOVIM_BITACORA_DESC_ALTA = "Alta";
        public static final String MOVIM_BITACORA_COD_MODIF = "M";
        public static final String MOVIM_BITACORA_DESC_MODIF = "Actualización";
    }

    public static class GrupoParametro{
        public static final BigInteger GRUPO_NUM_DOCUMENTO = BigInteger.valueOf(10010L);
    }

    public static class Parametros{
        public static final BigInteger PARAM_BANCO_EX_EXPRESS = BigInteger.valueOf(10027L);
        public static final BigInteger PARAM_TDOC_DNI = BigInteger.valueOf(10024L);
        public static final BigInteger PARAM_TDOC_RUC = BigInteger.valueOf(10025L);
    }

    public static class Perfil{
        public static final BigInteger ROL_WEB = BigInteger.valueOf(300L);
    }

    public static class Entidad{
        public static final BigInteger SOLICITUD = BigInteger.valueOf(2007L);
        public static final BigInteger USUARIO = BigInteger.valueOf(2003L);
        public static final BigInteger GRUPO_PARAMETRO = BigInteger.valueOf(1000L);
        public static final BigInteger PARAMETRO = BigInteger.valueOf(1001L);
        public static final BigInteger UBIGEO = BigInteger.valueOf(1006L);
        public static final BigInteger ROL = BigInteger.valueOf(2002L);
    }

    public static class Monedas{
        public static final BigInteger PARAM_MONEDA_BASE = BigInteger.valueOf(10001L);
        public static final BigInteger PARAM_MONEDA_EURO = BigInteger.valueOf(10004L);
        public static final BigInteger PARAM_MONEDA_DOLAR = BigInteger.valueOf(10002L);
        public static final BigInteger PARAM_MONEDA_SOL = BigInteger.valueOf(10001L);
    }

    public static class SocioNegocioCuenta{
        public static final String EST_PEN = "P";
        public static final String EST_PEN_DESC = "Pendiente";
        public static final String EST_OBS = "O";
        public static final String EST_OBS_DESC = "Observado";
        public static final String EST_VAL = "V";
        public static final String EST_VAL_DESC = "Validada";

    }

    public static class Usuario{
        public static final String EST_PEN = "P";
        public static final String EST_PEN_DESC = "Pendiente";
        public static final String EST_OBS = "O";
        public static final String EST_OBS_DESC = "Observado";
        public static final String EST_VAL = "V";
        public static final String EST_VAL_DESC = "Validada";
    }
}
