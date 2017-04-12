package com.greip.core.service;

import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.SeRolDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface SeRolService {

    public SeRolDto save(SeRolDto dto) throws ServiceException;

    public SeRolDto getById(BigInteger id) throws ServiceException;

    public List<SeRolDto> gets(FiltroDto filtroDto) throws ServiceException;

    public List<SeRolDto> getsByNombre(String nombre) throws ServiceException;

    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException;
}
