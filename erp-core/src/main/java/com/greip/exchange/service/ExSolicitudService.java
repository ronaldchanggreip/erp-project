package com.greip.exchange.service;

import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.SeUsuarioDto;
import com.greip.core.exception.ServiceException;
import com.greip.exchange.dto.ExSolicitudDto;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface ExSolicitudService {

    public ExSolicitudDto save(ExSolicitudDto dto) throws ServiceException;

    public ExSolicitudDto getById(BigInteger id) throws ServiceException;

    public List<ExSolicitudDto> gets(FiltroDto filtroDto, SeUsuarioDto seUsuarioDto) throws ServiceException;

    public List<ExSolicitudDto> getBySocNegocioAndEtapaEstado(BigInteger socNegocio, String etapa, String estado) throws ServiceException;

    public int nuevaSolicitud(ExSolicitudDto dto) throws ServiceException;

    public String anular(List<ExSolicitudDto> lst) throws ServiceException;

    public String observarEnRevision(List<ExSolicitudDto> lst) throws ServiceException;

    public String abortar(List<ExSolicitudDto> lst) throws ServiceException;

    public int levantarObservacion(ExSolicitudDto dto) throws ServiceException;

    public String revisada(List<ExSolicitudDto> lst) throws ServiceException;

    public String observarEnValidacion(List<ExSolicitudDto> lst) throws ServiceException;

    public String validada(List<ExSolicitudDto> lst) throws ServiceException;

    public int ejecutada(ExSolicitudDto dto) throws ServiceException;

    public int actualizarPorSocio(ExSolicitudDto dto) throws ServiceException;

    public int actualizarPorExchange(ExSolicitudDto dto) throws ServiceException;

    public File generarPDF(BigInteger id);
}
