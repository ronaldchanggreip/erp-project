package com.greip.core.service;

import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeUbigeoDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface GeUbigeoService {

    public GeUbigeoDto save(GeUbigeoDto dto) throws ServiceException;

    public GeUbigeoDto getById(BigInteger id) throws ServiceException;

    public List<GeUbigeoDto> gets(FiltroDto filtroDto) throws ServiceException;

    public List<GeUbigeoDto> getsActivosPadre(BigInteger idPadre) throws ServiceException;

    public List<GeUbigeoDto> getsPadre(BigInteger idPadre) throws ServiceException;


}
