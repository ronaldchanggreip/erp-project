package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.repository.GeUbigeoRepository;
import com.greip.core.dto.GeUbigeoDto;
import com.greip.core.exception.ServiceException;
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
@Service("geUbigeoService")
@Transactional
public class GeUbigeoServiceImpl implements GeUbigeoService {

    @Autowired
    GeUbigeoRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public GeUbigeoDto save(GeUbigeoDto dto) throws ServiceException {
        if (dto != null) {
            dao.save(dto);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public GeUbigeoDto getById(BigInteger id) throws ServiceException {

        GeUbigeoDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeUbigeoDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<GeUbigeoDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from GeUbigeoDto dto ";
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
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }
        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeUbigeoDto> getsActivosPadre(BigInteger idPadre) throws ServiceException {
        List<GeUbigeoDto> lista = null;
        if (idPadre != null) {
            lista = (List<GeUbigeoDto>) dao.getByActivosPadre(idPadre, Boolean.TRUE);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeUbigeoDto> getsPadre(BigInteger idPadre) throws ServiceException {
        List<GeUbigeoDto> lista = null;
        if (idPadre != null) {
            lista = (List<GeUbigeoDto>) dao.getByPadre(idPadre);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }

        return lista;
    }


}
