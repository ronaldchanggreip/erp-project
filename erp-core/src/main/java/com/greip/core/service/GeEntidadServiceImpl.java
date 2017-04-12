package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.GeEntidadDto;
import com.greip.core.repository.GeEntidadRepository;
import com.greip.core.exception.RepositoryException;
import com.greip.core.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Service("geEntidadService")
@Transactional
public class GeEntidadServiceImpl implements GeEntidadService {

    @Resource
    GeEntidadRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public GeEntidadDto save(GeEntidadDto dto) throws ServiceException {

        if (dto != null) {
            try {
                dao.save(dto);
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }

        } else {
            throw new ServiceException(ServiceException.ENTIDAD_NOT_NULL);
        }
        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public GeEntidadDto getById(BigInteger id) throws ServiceException {
        if (id != null) {
            try {
                return dao.findOne(id);
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }

        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }

    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeEntidadDto> gets(GeEntidadDto dto) throws ServiceException {

        if (dto != null) {
            try {
                return (List<GeEntidadDto>) dao.findAll();
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }

        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeEntidadDto> getByModulo(String modulo) throws ServiceException {

        if (modulo != null && !modulo.isEmpty() && !modulo.equals("")) {
            return (List<GeEntidadDto>) dao.getByModulo(modulo.toUpperCase());
        } else {
            throw (new ServiceException("El modulo no puede ser nulo"));
        }

    }
}
