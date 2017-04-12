package com.greip.exchange.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.util.UtlFiltro;
import com.greip.exchange.dto.ExCuentaEmpresaDto;
import com.greip.exchange.repository.ExCuentaEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Service("exCuentaEmpresaService")
public class ExCuentaEmpresaServiceImpl implements ExCuentaEmpresaService {

    @Autowired
    ExCuentaEmpresaRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public ExCuentaEmpresaDto save(ExCuentaEmpresaDto dto) throws ServiceException {
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
            ExCuentaEmpresaDto dto = getById(id);
            dto.setFecha(auditoriaDto.getFecha());
            dto.setUsuarioDto(auditoriaDto.getUsuarioDto());
            dto.setTerminal(auditoriaDto.getTerminal());
            dto.setEliminado(Boolean.TRUE);
            dao.save(dto);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public ExCuentaEmpresaDto getById(BigInteger id) throws ServiceException {

        ExCuentaEmpresaDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<ExCuentaEmpresaDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<ExCuentaEmpresaDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from ExCuentaEmpresaDto dto ";
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
