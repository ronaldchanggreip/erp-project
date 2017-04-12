package com.greip.exchange.repository;

import com.greip.exchange.dto.ExTipoCambioDto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrador on 03/02/2017.
 */
public interface ExTipoCambioRepositoryCustom {

    public List<BigInteger> procedureTiposCambioDelDia(Date fecha);

    public boolean inactivarOtrosTiposCambios(BigInteger id);

    public List<ExTipoCambioDto> getsPorFecha(Date fecha, BigInteger moneda);


}
