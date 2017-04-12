package com.greip.core.service;

import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeSocioNegocioCuentaDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface GeSocioNegocioCuentaService {

    public GeSocioNegocioCuentaDto save(GeSocioNegocioCuentaDto dto) throws ServiceException;

    public GeSocioNegocioCuentaDto getById(BigInteger id) throws ServiceException;

    public List<GeSocioNegocioCuentaDto> gets(FiltroDto filtroDto) throws ServiceException;

    public void eliminarLogico(List<BigInteger> lst) throws ServiceException;
}
