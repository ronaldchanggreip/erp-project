package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.dto.GeMonedaDto;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import com.greip.core.repository.GeMonedaRepository;
import com.greip.core.util.UtlFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Service("geMonedaService")
@Transactional
public class GeMonedaServiceImpl implements GeMonedaService {

    @Autowired
    GeMonedaRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public GeMonedaDto save(GeMonedaDto dto) throws ServiceException {
        if (dto != null) {
            dao.save(dto);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException {
        for(BigInteger id : lst){
            GeMonedaDto dto = getById(id);
            dto.setFecha(auditoriaDto.getFecha());
            dto.setUsuarioDto(auditoriaDto.getUsuarioDto());
            dto.setTerminal(auditoriaDto.getTerminal());
            dto.setEliminado(Boolean.TRUE);
            dao.save(dto);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public GeMonedaDto getById(BigInteger id) throws ServiceException {

        GeMonedaDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeMonedaDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<GeMonedaDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from GeMonedaDto dto ";
            if(filtroTransDto!=null){
                if(filtroTransDto.getCondicion().length()>0) {
                    query += "where " + filtroTransDto.getCondicion();
                    lstParams = filtroTransDto.getParams();
                }
                if(filtroDto.isOrder() && filtroTransDto.getOrder().length()>0)
                    query += " order by " + filtroTransDto.getOrder();
            }
            lista = dao.ejecutarQuery(query, lstParams);
        }catch(Exception e){
            throw e;
        }
        return lista;
    }
}
