package com.greip.core.service;

import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeMonedaDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface GeMonedaService {

    public GeMonedaDto save(GeMonedaDto dto) throws ServiceException;

    public GeMonedaDto getById(BigInteger id) throws ServiceException;

    public List<GeMonedaDto> gets(FiltroDto filtroDto) throws ServiceException;

    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException;
}
