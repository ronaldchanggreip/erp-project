package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.dto.GeParametroDto;
import com.greip.core.repository.GeParametroRepository;
import com.greip.core.exception.ServiceException;
import com.greip.core.util.UtlFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Service("geParametroService")
public class GeParametroServiceImpl implements GeParametroService {
    @Autowired
    GeParametroRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public GeParametroDto save(GeParametroDto dto) throws ServiceException {
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
        for(BigInteger id : lst) {
            GeParametroDto dto = getById(id);
            dto.setFecha(auditoriaDto.getFecha());
            dto.setUsuarioDto(auditoriaDto.getUsuarioDto());
            dto.setTerminal(auditoriaDto.getTerminal());
            dto.setEliminado(Boolean.TRUE);
            dao.save(dto);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public GeParametroDto getById(BigInteger id) throws ServiceException {

        GeParametroDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeParametroDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<GeParametroDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from GeParametroDto dto ";
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
    public List<GeParametroDto> getsActivosGrupo(BigInteger idGrupo) throws ServiceException {
        List<GeParametroDto> lista = null;
        if (idGrupo != null) {
            lista = (List<GeParametroDto>) dao.getByGrupoEstado(idGrupo, Boolean.TRUE);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }

        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeParametroDto> getsGrupo(BigInteger idGrupo) throws ServiceException {
        List<GeParametroDto> lista = null;
        if (idGrupo != null) {
            lista = (List<GeParametroDto>) dao.getByGrupo(idGrupo);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return lista;
    }


}
