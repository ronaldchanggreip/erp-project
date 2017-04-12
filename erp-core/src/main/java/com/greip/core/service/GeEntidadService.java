package com.greip.core.service;

import com.greip.core.dto.GeEntidadDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeEntidadService {

    public GeEntidadDto save(GeEntidadDto dto) throws ServiceException;

    public GeEntidadDto getById(BigInteger id) throws ServiceException;

    public List<GeEntidadDto> gets(GeEntidadDto dto) throws ServiceException;

    public List<GeEntidadDto> getByModulo(String modulo) throws ServiceException;


}
