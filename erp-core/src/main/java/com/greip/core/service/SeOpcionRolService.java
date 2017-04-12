package com.greip.core.service;

import com.greip.core.dto.SeOpcionRolDto;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface SeOpcionRolService {

    public SeOpcionRolDto save(SeOpcionRolDto dto) throws ServiceException;

    public SeOpcionRolDto getById(BigInteger id) throws ServiceException;

    public List<SeOpcionRolDto> gets(SeOpcionRolDto dto) throws ServiceException;

    public List<SeOpcionRolDto> getByRol(BigInteger idRol) throws ServiceException;

    public List<SeOpcionRolDto> getActivosByRol(BigInteger idRol) throws ServiceException;

    public SeOpcionRolDto getByRolOsNombre(BigInteger idRol, String osNombre) throws ServiceException;

    public void estadoOpcionesPorRol(Boolean estado, BigInteger idRol)  throws ServiceException;

    public List<Object> opcionesParaMenu(BigInteger idRol) throws ServiceException;
}
