package com.greip.exchange.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.AuditoriaDto;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.util.UtlDate;
import com.greip.core.util.UtlFiltro;
import com.greip.exchange.dto.ExTipoCambioDto;
import com.greip.exchange.repository.ExTipoCambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Service("exTipoCambioService")
public class ExTipoCambioServiceImpl implements ExTipoCambioService {

    @Autowired
    ExTipoCambioRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public ExTipoCambioDto save(ExTipoCambioDto dto) throws ServiceException {
        if (dto != null) {
            dto.setEstado("A"); //Activo por default
            dao.save(dto);
            boolean retorno = dao.inactivarOtrosTiposCambios(dto.getId());

        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException {
        for(BigInteger id : lst){
            ExTipoCambioDto dto = getById(id);
            dto.setFecha(auditoriaDto.getFecha());
            dto.setUsuarioDto(auditoriaDto.getUsuarioDto());
            dto.setTerminal(auditoriaDto.getTerminal());
            dto.setEliminado(Boolean.TRUE);
            dao.save(dto);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public ExTipoCambioDto getById(BigInteger id) throws ServiceException {

        ExTipoCambioDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<ExTipoCambioDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<ExTipoCambioDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from ExTipoCambioDto dto ";
            if(filtroTransDto!=null){
                if(filtroTransDto.getCondicion().length()>0) {
                    query += "where " + filtroTransDto.getCondicion();
                    lstParams = filtroTransDto.getParams();
                }
                if(filtroDto.isOrder() && filtroTransDto.getOrder().length()>0)
                    query += " order by " + filtroTransDto.getOrder();
            }
            lista = dao.ejecutarQuery(query, lstParams, filtroDto.isLimit(), filtroDto.getFirst(), filtroDto.getMax());
        }catch(Exception e){
            throw e;
        }
        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public ExTipoCambioDto getTipoCambioVigente(BigInteger banco, BigInteger mOrigen, BigInteger mDestino, Date fecha) throws ServiceException {
        ExTipoCambioDto dto = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(fecha);
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);

            Calendar c2 = Calendar.getInstance();
            c2.setTime(fecha);
            c2.set(Calendar.HOUR_OF_DAY, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            c2.set(Calendar.MILLISECOND, 999);


            String query = "select dto from ExTipoCambioDto dto ";
            query += "where dto.bancoDto.id = ?";
            lstParams.add(banco);
            query += " and dto.monedaOrigenDto.id = ?";
            lstParams.add(mOrigen);
            query += " and dto.monedaDestinoDto.id = ?";
            lstParams.add(mDestino);
            query += " and dto.fechaVigencia between ? and ?";
            lstParams.add(c1.getTime());
            lstParams.add(c2.getTime());
            query += " order by fechaVigencia desc" ;
            List<ExTipoCambioDto> lista = dao.ejecutarQuery(query, lstParams, true, 0, 1);
            if(lista!=null && !lista.isEmpty())
                dto = lista.get(0);
        }catch(Exception e){
            throw e;
        }
        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<ExTipoCambioDto> procedureTiposCambioDelDia(Date fecha)  throws ServiceException {
        List<ExTipoCambioDto> result = null;
        try {
            List<BigInteger> lst =  dao.procedureTiposCambioDelDia(fecha);
            if(lst!=null && !lst.isEmpty()){
                result = new ArrayList<>();
                for(BigInteger bi : lst){
                    result.add(dao.findOne(bi));
                }
            }
        }catch(Exception e){
            throw e;
        }
        return result;
    }
    @Override
    @ExceptionServiceAnnotation
    public List<ExTipoCambioDto> getsPorFecha(Date fecha, BigInteger moneda)  throws ServiceException {

        List<ExTipoCambioDto> lista = new ArrayList<>();
        ExTipoCambioDto tcExExVigente = null;

        try {
            if (fecha==null) {
                fecha = UtlDate.obtenerFechaActualDate();
            }
            lista = dao.getsPorFecha(UtlDate.obtenerFechaActualDate(), moneda);


            for (ExTipoCambioDto d: lista) {
                if (d.getBancoDto().getId().equals(ConstantesCore.Parametros.PARAM_BANCO_EX_EXPRESS)) {
                    tcExExVigente = d;
                }
            }


                for (ExTipoCambioDto d1: lista) {
                    if (tcExExVigente.getMonedaOrigenDto().getId().equals(d1.getMonedaOrigenDto().getId()) &&
                            tcExExVigente.getMonedaDestinoDto().getId().equals(d1.getMonedaDestinoDto().getId())) {
                        d1.setPrecioCompraDif((tcExExVigente.getPrecioCompra().multiply(BigDecimal.valueOf(-1))).add(d1.getPrecioCompra()));
                        d1.setPrecioVentaDif((tcExExVigente.getPrecioVenta().multiply(BigDecimal.valueOf(-1))).add(d1.getPrecioVenta()));
                    }
                }



            return lista;

        }catch(Exception e){
            throw e;
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public ExTipoCambioDto getExchangePorFecha(Date fecha, BigInteger moneda)  throws ServiceException {
        List<ExTipoCambioDto> lista = this.getsPorFecha(fecha, moneda);

        for (ExTipoCambioDto tc: lista) {
            if (tc.getBancoDto().getId().equals(ConstantesCore.Parametros.PARAM_BANCO_EX_EXPRESS)) {
                return tc;
            }
        }
        return null;

    }


}
