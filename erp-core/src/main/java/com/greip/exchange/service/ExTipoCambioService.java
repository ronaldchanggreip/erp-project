package com.greip.exchange.service;

import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.exception.ServiceException;
import com.greip.exchange.dto.ExTipoCambioDto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface ExTipoCambioService {

    public ExTipoCambioDto save(ExTipoCambioDto dto) throws ServiceException;

    public ExTipoCambioDto getById(BigInteger id) throws ServiceException;

    public List<ExTipoCambioDto> gets(FiltroDto filtroDto) throws ServiceException;

    public ExTipoCambioDto getTipoCambioVigente(BigInteger banco, BigInteger mOrigen, BigInteger mDestino, Date fecha) throws ServiceException;

    public List<ExTipoCambioDto> procedureTiposCambioDelDia(Date fecha)  throws ServiceException;

    public List<ExTipoCambioDto> getsPorFecha(Date fecha, BigInteger moneda)  throws ServiceException ;

    public ExTipoCambioDto getExchangePorFecha(Date fecha, BigInteger moneda)  throws ServiceException;

    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException;
}
