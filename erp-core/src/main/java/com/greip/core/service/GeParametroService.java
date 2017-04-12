package com.greip.core.service;

import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeParametroDto;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface GeParametroService{

    public GeParametroDto save(GeParametroDto dto) throws ServiceException;

    public GeParametroDto getById(BigInteger id) throws ServiceException;

    public List<GeParametroDto> gets(FiltroDto filtroDto) throws ServiceException;

    public List<GeParametroDto> getsActivosGrupo(BigInteger idGrupo) throws ServiceException;

    public List<GeParametroDto> getsGrupo(BigInteger idGrupo) throws ServiceException;

    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException;
}
