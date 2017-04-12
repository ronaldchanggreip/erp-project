package com.greip.exchange.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.dto.SeUsuarioDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.service.GeComunService;
import com.greip.core.service.SeUsuarioService;
import com.greip.core.util.UtlDate;
import com.greip.core.util.UtlFiltro;
import com.greip.exchange.dto.*;
import com.greip.exchange.repository.ExSolicitudRepository;
import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Service("exSolicitudService")
public class ExSolicitudServiceImpl implements ExSolicitudService {

    public static Logger log = Logger.getLogger(ExSolicitudServiceImpl.class);

    @Autowired
    ExSolicitudRepository dao;
    @Autowired
    ExTipoCambioService tCambioService;
    @Autowired
    SeUsuarioService usuarioService;
    @Autowired
    GeComunService geComunService;

    @Override
    @ExceptionServiceAnnotation
    public ExSolicitudDto save(ExSolicitudDto dto) throws ServiceException {
        if (dto != null) {
            dao.save(dto);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    /**
     *
     * @param dto
     * @return resultado {0: Ocurrió un error al guardar, 1: Ok, 2: Usuario tiene otra sol. en etapa REGISTRO y estado PENDIENTE, 3: El tipo de cambio ha cambiado}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public int nuevaSolicitud(ExSolicitudDto dto) throws ServiceException {
        int resultado = 0;
        if (dto != null) {
            try{
                //Si es nueva verificamos si el socio de negocio cuenta con una solicitud en estado PENDIENTE y en la etapa REGISTRO
                //List<ExSolicitudDto> lstSol = this.getBySocNegocioAndEtapaEstado(dto.getSocioNegocioDto().getId(), ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REGISTRO, ConstantesCore.ValoresPorDefecto.ESTSOL_COD_PENDIENTE);
                List<ExSolicitudDto> lstSol = null; //Ya no validamos RCH 20170326
                if(lstSol != null && !lstSol.isEmpty()){
                    resultado = 2;
                }else{
                    boolean flagTC = true;
                    if(!dto.isFlagConfirm()) {
                        //Verificamos que el tipo de cambio no haya cambiado
                        BigInteger mOrigen = null;
                        BigInteger mDestino = null;
                        if(dto.getMonedaOrigenDto().getId().equals(ConstantesCore.Monedas.PARAM_MONEDA_DOLAR) && dto.getMonedaDestinoDto().getId().equals(ConstantesCore.Monedas.PARAM_MONEDA_SOL)){
                            mOrigen = ConstantesCore.Monedas.PARAM_MONEDA_SOL;
                            mDestino = ConstantesCore.Monedas.PARAM_MONEDA_DOLAR;
                        }

                        if(dto.getMonedaOrigenDto().getId().equals(ConstantesCore.Monedas.PARAM_MONEDA_EURO) && dto.getMonedaDestinoDto().getId().equals(ConstantesCore.Monedas.PARAM_MONEDA_SOL)){
                            mOrigen = ConstantesCore.Monedas.PARAM_MONEDA_SOL;
                            mDestino = ConstantesCore.Monedas.PARAM_MONEDA_EURO;
                        }


                        ExTipoCambioDto tcDto = tCambioService.getTipoCambioVigente(ConstantesCore.Parametros.PARAM_BANCO_EX_EXPRESS, mOrigen, mDestino, UtlDate.obtenerFechaActualDate());
                        if (tcDto!=null && !dto.getTipoCambioDto().getId().equals(tcDto.getId())) {
                            //Si son diferentes registros verificamos que el factor tambien sea diferente
                            if(dto.getTransaccion().equals("C"))
                                if (!dto.getTipoCambioDto().getPrecioCompra().equals(tcDto.getFactor()))
                                    flagTC = false;
                            else
                                if (!dto.getTipoCambioDto().getPrecioVenta().equals(tcDto.getFactor()))
                                    flagTC = false;
                        }
                    }

                    if(!flagTC)
                        resultado = 3;
                    else{
                        dto.setEtapa(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REGISTRO);
                        dto.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_PENDIENTE);
                        dao.save(dto);
                        resultado = 1;
                    }

                }
            }catch(Exception ex){
                throw ex;
            }
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }
        return resultado;
    }

    @Override
    @ExceptionServiceAnnotation
    public ExSolicitudDto getById(BigInteger id) throws ServiceException {

        ExSolicitudDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }
        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<ExSolicitudDto> gets(FiltroDto filtroDto, SeUsuarioDto seUsuarioDto) throws ServiceException {
        List<ExSolicitudDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from ExSolicitudDto dto ";
            if(filtroTransDto!=null){
                if(filtroTransDto.getCondicion().length()>0) {
                    query += "where (" + filtroTransDto.getCondicion();
                    lstParams = filtroTransDto.getParams();
                }
                query += " )";
                //Verificamos si se trata de un socio de negocio o no
                if(seUsuarioDto.getSocioNegocio()!=null){
                    query += " and dto.socioNegocioDto.id = ?";
                    lstParams.add(seUsuarioDto.getSocioNegocio());
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

    @Override
    @ExceptionServiceAnnotation
    public List<ExSolicitudDto> getBySocNegocioAndEtapaEstado(BigInteger socNegocio, String etapa, String estado) throws ServiceException {
        List<ExSolicitudDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            String query = "select dto from ExSolicitudDto dto ";
            query += " where dto.socioNegocioDto.id = ?";
            lstParams.add(socNegocio);
            query += " and dto.etapa = '" + etapa + "'";
            query += " and dto.estado = '" + estado + "'";
            lista = dao.ejecutarQuery(query, lstParams);
        }catch(Exception e){
            throw e;
        }
        return lista;
    }

    /**
     *
     * @param lst
     * @return resultado {'0': error, '1': exito}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public String anular(List<ExSolicitudDto> lst) throws ServiceException {
        String resultado = "";
        try {
            ExSolicitudDto dtoNow;
            boolean flag = true;
            for(ExSolicitudDto dto : lst) {
                dtoNow = this.getById(dto.getId());
                if (dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_EJECUTADA) || dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ANULADA) || dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ABORTADA)){
                    resultado += ", " + dto.getId();
                    flag = false;
                }else{
                    dtoNow.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ANULADA);
                    dtoNow.setComentario(dto.getComentario());
                    //Copiamos los datos de auditoria
                    dtoNow.setFecha(dto.getFecha());
                    dtoNow.setUsuarioDto(dto.getUsuarioDto());
                    dtoNow.setTerminal(dto.getTerminal());
                    dao.save(dtoNow);
                }
            }

            if(flag)
                resultado = "1";
            else {
                resultado = resultado.substring(1, resultado.length());
            }
        }catch (Exception e){
            resultado = "0";
            throw e;
        }
        return resultado;
    }

    /**
     *
     * @param lst
     * @return resultado {'0': error, '1': exito}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public String abortar(List<ExSolicitudDto> lst) throws ServiceException {
        String resultado = "";
        try {
            ExSolicitudDto dtoNow;
            boolean flag = true;
            for(ExSolicitudDto dto : lst) {
                dtoNow = this.getById(dto.getId());
                if (dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_EJECUTADA) || dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ANULADA) || dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ABORTADA)){
                    resultado += ", " + dto.getId();
                    flag = false;
                }else {
                    dtoNow.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ABORTADA);
                    dtoNow.setComentario(dto.getComentario());
                    //Copiamos los datos de auditoria
                    dtoNow.setFecha(dto.getFecha());
                    dtoNow.setUsuarioDto(dto.getUsuarioDto());
                    dtoNow.setTerminal(dto.getTerminal());
                    dao.save(dtoNow);
                }
            }

            if(flag)
                resultado = "1";
            else {
                resultado = resultado.substring(1, resultado.length());
            }
        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    /**
     *
     * @param lst
     * @return resultado {'0': error, '1': exito}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public String observarEnRevision(List<ExSolicitudDto> lst) throws ServiceException {
        String resultado = "";
        try {
            ExSolicitudDto dtoNow;
            boolean flag = true;
            for(ExSolicitudDto dto : lst) {
                dtoNow = this.getById(dto.getId());
                if (dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REGISTRO) ||(dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_VALIDACION))) {
                    dtoNow.setEtapa(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REVISION);
                    dtoNow.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_OBSERVADA);
                    dtoNow.setComentario(dto.getComentario());
                    //Copiamos los datos de auditoria
                    dtoNow.setFecha(dto.getFecha());
                    dtoNow.setUsuarioDto(dto.getUsuarioDto());
                    dtoNow.setTerminal(dto.getTerminal());
                    dao.save(dtoNow);
                } else {
                    resultado += ", " + dto.getId();
                    flag = false;
                }
            }

            if(flag)
                resultado = "1";
            else {
                resultado = resultado.substring(1, resultado.length());
            }
        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    /**
     * @param dto
     *
     * @return resultado {0: error, 1: exito, 2: la entidad no se encuentra observada}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public int levantarObservacion(ExSolicitudDto dto) throws ServiceException {
        int resultado = 0;
        try {
            ExSolicitudDto dtoNow = this.getById(dto.getId());
            if(!dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_OBSERVADA))
                resultado = 2;
            else{
                dto.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_PENDIENTE);
                dao.save(dto);
                resultado = 1;
            }
        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    /**
     *
     * @param lst
     * @return resultado {'0': error, '1': exito}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public String revisada(List<ExSolicitudDto> lst) throws ServiceException {
        String resultado = "";
        try {
            ExSolicitudDto dtoNow;
            boolean flag = true;
            for(ExSolicitudDto dto : lst) {
                dtoNow = this.getById(dto.getId());
                if ((dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REGISTRO) && (dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_PENDIENTE))) || (dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REVISION) && dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_PENDIENTE))) {
                    dtoNow.setEtapa(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_VALIDACION);
                    dtoNow.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_REVISADA);
                    dtoNow.setComentario(dto.getComentario());
                    //Copiamos los datos de auditoria
                    dtoNow.setFecha(dto.getFecha());
                    dtoNow.setUsuarioDto(dto.getUsuarioDto());
                    dtoNow.setTerminal(dto.getTerminal());
                    dao.save(dtoNow);
                } else {
                    resultado += ", " + dto.getId();
                    flag = false;
                }
            }

            if(flag)
                resultado = "1";
            else {
                resultado = resultado.substring(1, resultado.length());
            }
        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    /**
     *
     * @param lst
     * @return resultado {'0': error, '1': exito}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public String observarEnValidacion(List<ExSolicitudDto> lst) throws ServiceException {
        String resultado = "";
        try {
            ExSolicitudDto dtoNow;
            boolean flag = true;
            for(ExSolicitudDto dto : lst) {
                dtoNow = this.getById(dto.getId());
                if (dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_VALIDACION)) {
                    dtoNow.setEtapa(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_VALIDACION);
                    dtoNow.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_OBSERVADA);
                    dtoNow.setComentario(dto.getComentario());
                    //Copiamos los datos de auditoria
                    dtoNow.setFecha(dto.getFecha());
                    dtoNow.setUsuarioDto(dto.getUsuarioDto());
                    dtoNow.setTerminal(dto.getTerminal());
                    dao.save(dtoNow);
                } else {
                    resultado += ", " + dto.getId();
                    flag = false;
                }
            }

            if(flag)
                resultado = "1";
            else {
                resultado = resultado.substring(1, resultado.length());
            }
        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    /**
     *
     * @param lst
     * @return resultado {'0': error, '1': exito}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public String validada(List<ExSolicitudDto> lst) throws ServiceException {
        String resultado = "";
        try {
            ExSolicitudDto dtoNow;
            boolean flag = true;
            for(ExSolicitudDto dto : lst) {
                dtoNow = this.getById(dto.getId());
                if ((dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_VALIDACION) && dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_REVISADA)) || (dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_VALIDACION) && dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_PENDIENTE))) {
                    dtoNow.setEtapa(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_EJECUCION);
                    dtoNow.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_VALIDADA);
                    dtoNow.setComentario(dto.getComentario());
                    //Copiamos los datos de auditoria
                    dtoNow.setFecha(dto.getFecha());
                    dtoNow.setUsuarioDto(dto.getUsuarioDto());
                    dtoNow.setTerminal(dto.getTerminal());
                    dao.save(dtoNow);
                } else {
                    resultado += ", " + dto.getId();
                    flag = false;
                }
            }

            if(flag)
                resultado = "1";
            else {
                resultado = resultado.substring(1, resultado.length());
            }
        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    /**
     *
     * @param dto
     * @return resultado {0: error, 1: exito, 2: la entidad se encuentra en una etapa y estado en la que no puede marcarse como ejecutada}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public int ejecutada(ExSolicitudDto dto) throws ServiceException {
        int resultado = 0;
        try {
            ExSolicitudDto dtoNow = this.getById(dto.getId());
            if(dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_EJECUCION) && dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_VALIDADA)) {
                //dtoNow.setEtapa(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_EJECUCION);
                dtoNow.setEstado(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_EJECUTADA);
                dtoNow.setComentario(dto.getComentario());
                //Copiamos los datos de auditoria
                dtoNow.setFecha(dto.getFecha());
                dtoNow.setUsuarioDto(dto.getUsuarioDto());
                dtoNow.setTerminal(dto.getTerminal());

                //Aqui debemos poner los datos que se ingresan en la ejecucion
                dtoNow.setBancoDestinoEmpDto(dto.getBancoDestinoEmpDto());
                dtoNow.setcEmpDestinoDto(dto.getcEmpDestinoDto());
                dtoNow.setNumVoucherDestino(dto.getNumVoucherDestino());

                dao.save(dtoNow);
                resultado = 1;
            }else{
                resultado = 2;
            }
        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    /**
     *
     * @param dto
     * @return resultado {0: error, 1: exito, 2: la entidad se encuentra en una etapa y estado en la que no puede ser modificada}
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public int actualizarPorSocio(ExSolicitudDto dto) throws ServiceException{
        int resultado = 0;
        try {
            ExSolicitudDto dtoNow = this.getById(dto.getId());
            if((dtoNow.getEtapa().equals(ConstantesCore.ValoresPorDefecto.ETAPASOL_COD_REGISTRO) && dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_PENDIENTE))
                    || dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_OBSERVADA)){
                dao.save(dto);
                resultado = 1;
            }else{
                resultado = 2;
            }

        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    /**
     *
     * @param dto resultado {0: error, 1: exito, 2: la entidad se encuentra en una etapa y estado en la que no puede ser modificada}
     * @return
     * @throws ServiceException
     */
    @Override
    @ExceptionServiceAnnotation
    public int actualizarPorExchange(ExSolicitudDto dto) throws ServiceException{
        int resultado = 0;
        try {
            ExSolicitudDto dtoNow = this.getById(dto.getId());
            if(!(dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ANULADA) || dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_ABORTADA)
                    || dtoNow.getEstado().equals(ConstantesCore.ValoresPorDefecto.ESTSOL_COD_EJECUTADA))){
                dao.save(dto);
                resultado = 1;
            }else{
                resultado = 2;
            }

        }catch (Exception e){
            throw e;
        }
        return resultado;
    }

    @Override
    @ExceptionServiceAnnotation
    public File generarPDF(BigInteger id) {
        File fileRetorno = null;
        try {
            String jasper = "rptSolicitudTransaccion";
            String nombreArchivoReporte = "SOL_NRO_" + id.toString() + ".pdf";
            HashMap parametros = new HashMap();
            parametros.put("P_ID_SOLICITUD", Integer.valueOf(id.toString()));
            String pathRetorno = geComunService.viewReportePdf(parametros, jasper, nombreArchivoReporte);
            if(pathRetorno!=null && pathRetorno.length()>0)
                fileRetorno = new File(pathRetorno);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (JRException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        } catch (SQLException ex) {
            log.error(ex);
        }
        return fileRetorno;
    }
}
