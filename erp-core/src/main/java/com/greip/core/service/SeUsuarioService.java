package com.greip.core.service;

import com.greip.core.dto.*;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface SeUsuarioService {

    public int save(SeUsuarioDto dto) throws ServiceException;

    public SeUsuarioDto getById(BigInteger id) throws ServiceException;

    public List<SeUsuarioDto> gets(FiltroDto filtroDto) throws ServiceException;

    public List<SeUsuarioDto> getByLoginEmail(String loginEmail) throws ServiceException;

    public List<SeOpcionDto> getOpcionesActivasByUsuario(BigInteger idUsuario) throws ServiceException;

    public List<SeUsuarioDto> getByLoginEmailExcepto(BigInteger id, String loginEmail) throws ServiceException;

    public int savePerfil(SeUsuarioDto dto) throws ServiceException;

    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException;

    public RptaServiceDto recuperarContrasena(String loginEmail) throws ServiceException;
}
