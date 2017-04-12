package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.dto.GeSocioNegocioCuentaDto;
import com.greip.core.repository.GeSocioNegocioCuentaRepository;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import com.greip.core.util.UtlFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Service("geSocioNegocioCuentaService")
public class GeSocioNegocioCuentaServiceImpl implements GeSocioNegocioCuentaService{

    @Autowired
    GeSocioNegocioCuentaRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public GeSocioNegocioCuentaDto save(GeSocioNegocioCuentaDto dto) throws ServiceException {
        if (dto != null) {
            if (dto.getIndCuentaValida()==null ||dto.getIndCuentaValida().isEmpty()){
                dto.setIndCuentaValida(ConstantesCore.SocioNegocioCuenta.EST_PEN);
            }
            dao.save(dto);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public void eliminarLogico(List<BigInteger> lst) throws ServiceException {
        for(BigInteger id : lst){
            GeSocioNegocioCuentaDto dto = getById(id);
            dto.setEliminado(Boolean.TRUE);
            dao.save(dto);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public GeSocioNegocioCuentaDto getById(BigInteger id) throws ServiceException {

        GeSocioNegocioCuentaDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeSocioNegocioCuentaDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<GeSocioNegocioCuentaDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from GeSocioNegocioCuentaDto dto ";
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
