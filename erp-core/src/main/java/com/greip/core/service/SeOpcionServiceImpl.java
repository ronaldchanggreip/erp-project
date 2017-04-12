package com.greip.core.service;

import com.google.gson.Gson;
import com.greip.core.dto.SeOpcionDto;
import com.greip.core.dto.TreeWebDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.repository.SeOpcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 17/02/2017.
 */
@Service("seOpcionService")
public class SeOpcionServiceImpl implements  SeOpcionService{

    @Autowired
    private SeOpcionRepository dao;

    @Override
    public SeOpcionDto getByNombre(String nombre) throws ServiceException {
        SeOpcionDto seOpcionDto = null;
        try {
            List<SeOpcionDto> lst = dao.getByNombre(nombre);
            if(lst!=null && !lst.isEmpty()){
                seOpcionDto = lst.get(0);
            }
        }catch (Exception ex){
            throw (new ServiceException(ServiceException.ERROR_DB));
        }
        return seOpcionDto;
    }

    @Override
    public List<TreeWebDto> generarArbol() throws ServiceException {
        List<TreeWebDto> lst = new ArrayList<>();
        try {
            List<SeOpcionDto> lstSoloPadres = dao.getByPadreEstado(BigInteger.ZERO, Boolean.TRUE);
            if(lstSoloPadres!= null && !lstSoloPadres.isEmpty()){
                for(SeOpcionDto o : lstSoloPadres) {
                    Long cantHijos = dao.cantidadHijosActivos(o.getId(), Boolean.TRUE);
                    TreeWebDto tDto = new TreeWebDto(o.getEtiqueta(), o.getNombre(), o.getExpandedIcon(), o.getIcon());
                    recursividadArbol(tDto, o);
                    lst.add(tDto);
                }
            }
            Gson gson = new Gson();
            //System.out.println("GSON : " + gson.toJson(lst));
        }catch (Exception ex){
            throw (new ServiceException(ServiceException.ERROR_DB));
        }
        return lst;
    }

    private void recursividadArbol(TreeWebDto trDto, SeOpcionDto padreDto){
        List<SeOpcionDto> lstHijos = dao.getByPadreEstado(padreDto.getId(), Boolean.TRUE);
        for(SeOpcionDto o : lstHijos){
            List<SeOpcionDto> lstHijos2 = dao.getByPadreEstado(o.getId(), Boolean.TRUE);
            if(lstHijos2!=null && !lstHijos2.isEmpty()){
                TreeWebDto tDto = new TreeWebDto(o.getEtiqueta(), o.getNombre(), o.getExpandedIcon(), o.getIcon());
                recursividadArbol(tDto, o);
                if(trDto.getChildren()==null)
                    trDto.setChildren(new ArrayList<>());
                trDto.getChildren().add(tDto);
            }else{
                TreeWebDto tDto = new TreeWebDto(o.getEtiqueta(), o.getIcon(), o.getNombre());
                if(trDto.getChildren()==null)
                    trDto.setChildren(new ArrayList<>());
                trDto.getChildren().add(tDto);
            }
        }

    }
}
