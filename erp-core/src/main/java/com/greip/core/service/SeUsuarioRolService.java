package com.greip.core.service;

import com.greip.core.dto.SeUsuarioRolDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface SeUsuarioRolService {

    public SeUsuarioRolDto save(SeUsuarioRolDto dto) throws ServiceException;

    public SeUsuarioRolDto getById(BigInteger id) throws ServiceException;

    public List<SeUsuarioRolDto> gets(SeUsuarioRolDto dto) throws ServiceException;

    public List<SeUsuarioRolDto> getByUsuario(BigInteger idUsuario) throws ServiceException;

    public List<SeUsuarioRolDto> getByRol(BigInteger idRol) throws ServiceException;

    public List<SeUsuarioRolDto> getByRolUsuario(BigInteger idRol, BigInteger idUsuario) throws ServiceException;


}
