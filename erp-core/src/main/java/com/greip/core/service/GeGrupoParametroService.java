package com.greip.core.service;

import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeGrupoParametroDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface GeGrupoParametroService {

    GeGrupoParametroDto save(GeGrupoParametroDto dto) throws ServiceException;

    GeGrupoParametroDto getById(BigInteger id) throws ServiceException;

    List<GeGrupoParametroDto> gets(FiltroDto filtroDto) throws ServiceException;


}
