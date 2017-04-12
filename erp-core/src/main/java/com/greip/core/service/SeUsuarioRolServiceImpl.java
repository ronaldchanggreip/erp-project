package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.SeUsuarioRolDto;
import com.greip.core.repository.SeUsuarioRolRepository;
import com.greip.core.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Service("geUsuarioRolService")
@Transactional
public class SeUsuarioRolServiceImpl implements SeUsuarioRolService {

    @Resource
    SeUsuarioRolRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public SeUsuarioRolDto save(SeUsuarioRolDto dto) throws ServiceException {

        if (dto != null) {
            dao.save(dto);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    @Override
    public SeUsuarioRolDto getById(BigInteger id) throws ServiceException {
        if (id != null) {
            return dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }

    }

    @Override
    public List<SeUsuarioRolDto> gets(SeUsuarioRolDto dto) throws ServiceException {

        if (dto != null) {
            return (List<SeUsuarioRolDto>) dao.findAll();
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }
    }

    @Override
    public List<SeUsuarioRolDto> getByUsuario(BigInteger idUsuario) throws ServiceException {

        if (idUsuario != null) {
            return (List<SeUsuarioRolDto>) dao.getByUsuario(idUsuario);
        } else {
            throw (new ServiceException("El id de usuario no puede ser nulo"));
        }
    }

    @Override
    public List<SeUsuarioRolDto> getByRol(BigInteger idRol) throws ServiceException {

        if (idRol != null) {
            return (List<SeUsuarioRolDto>) dao.getByRol(idRol);
        } else {
            throw (new ServiceException("El id de rol no puede ser nulo"));
        }
    }

    @Override
    public List<SeUsuarioRolDto> getByRolUsuario(BigInteger idRol, BigInteger idUsuario) throws ServiceException {

        if (idRol != null && idUsuario != null) {
            return (List<SeUsuarioRolDto>) dao.getByRolUsuario(idRol, idUsuario);
        } else {
            throw (new ServiceException("El id de rol y de usuario no puede ser nulo"));
        }
    }

}
