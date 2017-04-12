package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.dto.GeBitacoraDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.repository.GeBitacoraRepository;
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
@Service("geBitacoraService")
@Transactional
public class GeBitacoraServiceImpl implements GeBitacoraService {

    @Autowired
    GeBitacoraRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public GeBitacoraDto save(GeBitacoraDto dto) throws ServiceException {
        if (dto != null) {
            dao.save(dto);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public GeBitacoraDto getById(BigInteger id) throws ServiceException {

        GeBitacoraDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeBitacoraDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<GeBitacoraDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from GeBitacoraDto dto ";
            if(filtroTransDto!=null){
                if(filtroTransDto.getCondicion().length()>0) {
                    query += "where " + filtroTransDto.getCondicion();
                    lstParams = filtroTransDto.getParams();
                }
                if(filtroDto.isOrder() && filtroTransDto.getOrder().length()>0)
                    query += " order by " + filtroTransDto.getOrder();
            }
            //System.out.println(query);
            lista = dao.ejecutarQuery(query, lstParams);
        }catch(Exception e){
            throw e;
        }
        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeBitacoraDto> gets(BigInteger idEntidad, BigInteger idRegistro) throws ServiceException {
        List<GeBitacoraDto> lista = null;

        if (idEntidad != null && idRegistro != null) {
            lista = dao.getByEntidadRegistro(idEntidad, idRegistro);
        } else {
            throw (new ServiceException("La entidad y el registro no deben ser nulos"));
        }
        return lista;
    }

}
