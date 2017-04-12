package com.greip.core.service;

import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeBitacoraDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface GeBitacoraService {

    public GeBitacoraDto save(GeBitacoraDto dto) throws ServiceException;

    public GeBitacoraDto getById(BigInteger id) throws ServiceException;

    public List<GeBitacoraDto> gets(FiltroDto filtroDto) throws ServiceException;

    public List<GeBitacoraDto> gets(BigInteger idEntidad, BigInteger idRegistro) throws ServiceException;


}
