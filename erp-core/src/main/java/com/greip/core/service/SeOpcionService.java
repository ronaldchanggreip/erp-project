package com.greip.core.service;

import com.greip.core.dto.SeOpcionDto;
import com.greip.core.dto.TreeWebDto;
import com.greip.core.exception.ServiceException;

import java.util.List;

/**
 * Created by Administrador on 17/02/2017.
 */
public interface SeOpcionService {

    public List<TreeWebDto> generarArbol() throws ServiceException;

    public SeOpcionDto getByNombre(String nombre) throws ServiceException;
}
