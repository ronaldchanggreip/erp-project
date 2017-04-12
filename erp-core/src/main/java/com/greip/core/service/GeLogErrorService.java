package com.greip.core.service;

import com.greip.core.dto.GeLogErrorDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface GeLogErrorService {

    public GeLogErrorDto save(GeLogErrorDto dto) throws ServiceException;

    public GeLogErrorDto getById(BigInteger id) throws ServiceException;

    public List<GeLogErrorDto> gets(GeLogErrorDto dto) throws ServiceException;


}
