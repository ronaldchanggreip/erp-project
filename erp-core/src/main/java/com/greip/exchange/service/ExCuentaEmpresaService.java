package com.greip.exchange.service;

import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.exception.ServiceException;
import com.greip.exchange.dto.ExCuentaEmpresaDto;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface ExCuentaEmpresaService {

    public ExCuentaEmpresaDto save(ExCuentaEmpresaDto dto) throws ServiceException;

    public ExCuentaEmpresaDto getById(BigInteger id) throws ServiceException;

    public List<ExCuentaEmpresaDto> gets(FiltroDto filtroDto) throws ServiceException;

    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException;
}
