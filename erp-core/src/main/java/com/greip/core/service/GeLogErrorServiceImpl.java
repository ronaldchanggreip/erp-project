package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.GeLogErrorDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.repository.GeLogErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Service("geLogErrorService")
@Transactional
public class GeLogErrorServiceImpl implements GeLogErrorService {

    @Autowired
    GeLogErrorRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public GeLogErrorDto save(GeLogErrorDto dto) throws ServiceException {
        if (dto != null) {
            dao.save(dto);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public GeLogErrorDto getById(BigInteger id) throws ServiceException {

        GeLogErrorDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeLogErrorDto> gets(GeLogErrorDto dto) throws ServiceException {
        List<GeLogErrorDto> lista = null;
        if (dto != null) {
            lista = (List<GeLogErrorDto>) dao.findAll();
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return lista;
    }


}
