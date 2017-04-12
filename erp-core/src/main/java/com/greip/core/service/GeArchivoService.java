package com.greip.core.service;

import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.GeArchivoDto;
import com.greip.core.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */

public interface GeArchivoService {

    public GeArchivoDto save(GeArchivoDto dto) throws ServiceException;

    public GeArchivoDto getById(BigInteger id) throws ServiceException;

    public List<GeArchivoDto> gets(FiltroDto filtroDto) throws ServiceException;

    public List<GeArchivoDto> gets(BigInteger idEntidad, BigInteger idRegistro) throws ServiceException;

    public List<GeArchivoDto> guardarArchivos(MultipartFile[] files, BigInteger entidad, BigInteger registro) throws ServiceException;

    public void eliminarVarios(List<GeArchivoDto> lst) throws ServiceException;

    public List<GeArchivoDto> modificarVarios(List<GeArchivoDto> lst) throws ServiceException;

}
