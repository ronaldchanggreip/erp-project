package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.*;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import com.greip.core.repository.GeSocioNegocioRepository;
import com.greip.core.util.UtlFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
@Service("geSocioNegocioService")
public class GeSocioNegocioServiceImpl implements GeSocioNegocioService{

    @Autowired
    GeSocioNegocioRepository dao;
    @Autowired
    SeUsuarioService usuarioService;


    @Override
    @ExceptionServiceAnnotation
    public int save(GeSocioNegocioDto dto) throws ServiceException {
        int retorno = 1;
        if (dto != null) {
            String nombreCompleto = "";
            if(dto.getTipoDocumentoDto().getId().equals(ConstantesCore.Parametros.PARAM_TDOC_RUC))
                nombreCompleto = dto.getRazSocial();
            else
                nombreCompleto = dto.getNombres() + " " + dto.getApPaterno() + " " + dto.getApMaterno();

            dto.setNombreCompleto(nombreCompleto);
            boolean fValid = true;
            if(dto.getId() == null){
                GeSocioNegocioDto geSocNegoValid = getByDocumento(dto.getTipoDocumentoDto().getId(), dto.getNumDocumento());
                if(geSocNegoValid!=null)
                    fValid = false;
            }else{
                List<GeSocioNegocioDto> lstValid = getByDocumentoExcepto(dto.getId(), dto.getTipoDocumentoDto().getId(), dto.getNumDocumento());
                if(lstValid != null && !lstValid.isEmpty())
                    fValid = false;
            }
            if(fValid)
                dao.save(dto);
            else
                retorno = 2;

        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return retorno;
    }

    @Override
    @ExceptionServiceAnnotation
    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException {
        for(BigInteger id : lst){
            GeSocioNegocioDto dto = getById(id);
            dto.setFecha(auditoriaDto.getFecha());
            dto.setUsuarioDto(auditoriaDto.getUsuarioDto());
            dto.setTerminal(auditoriaDto.getTerminal());
            dto.setEliminado(Boolean.TRUE);
            dao.save(dto);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public GeSocioNegocioDto getById(BigInteger id) throws ServiceException {

        GeSocioNegocioDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public GeSocioNegocioDto getByDocumento(BigInteger tipo, String numero) throws ServiceException {

        GeSocioNegocioDto retornoDto = null;
        try{
            List<GeSocioNegocioDto> lst = dao.getByDocumento(tipo, numero);
            if(lst != null && !lst.isEmpty()){
                retornoDto = lst.get(0);
            }
        }catch(Exception e){
            throw e;
        }
        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeSocioNegocioDto> getByDocumentoExcepto(BigInteger id, BigInteger tipo, String numero) throws ServiceException {

        List<GeSocioNegocioDto> lst = null;
        try{
            lst = dao.getByDocumentoExcepto(id, tipo, numero);
        }catch(Exception e){
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }
        return lst;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeSocioNegocioDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<GeSocioNegocioDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from GeSocioNegocioDto dto ";
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

    @Override
    @ExceptionServiceAnnotation
    public GeSocioNegocioDto getByUsuario(String login) throws ServiceException{
        GeSocioNegocioDto dto = null;
        try {
            //Obtenemos el usuario
            List<SeUsuarioDto> lstUser = usuarioService.getByLoginEmail(login);
            if(lstUser!=null && !lstUser.isEmpty()) {
                SeUsuarioDto userDto = lstUser.get(0);
                if (userDto != null && userDto.getSocioNegocio() != null) {
                    dto = this.getById(userDto.getSocioNegocio());
                }
            }
        }catch (Exception e){
            throw e;
        }
        return dto;
    }
}
