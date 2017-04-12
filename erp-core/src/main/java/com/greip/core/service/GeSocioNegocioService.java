package com.greip.core.service;

import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeSocioNegocioDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface GeSocioNegocioService {

    public int save(GeSocioNegocioDto dto) throws ServiceException;

    public GeSocioNegocioDto getById(BigInteger id) throws ServiceException;

    public List<GeSocioNegocioDto> gets(FiltroDto filtroDto) throws ServiceException;

    public GeSocioNegocioDto getByUsuario(String login) throws ServiceException;

    public GeSocioNegocioDto getByDocumento(BigInteger tipo, String numero) throws ServiceException;

    public List<GeSocioNegocioDto> getByDocumentoExcepto(BigInteger id, BigInteger tipo, String numero) throws ServiceException;

    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException;
}
