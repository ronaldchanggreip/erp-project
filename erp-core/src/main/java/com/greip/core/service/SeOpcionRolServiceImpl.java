package com.greip.core.service;

import com.google.gson.Gson;
import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.MenuWebDto;
import com.greip.core.dto.SeOpcionDto;
import com.greip.core.dto.SeOpcionRolDto;
import com.greip.core.exception.ServiceException;
import com.greip.core.repository.SeOpcionRolRepository;
import com.greip.core.util.UtlGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Service("seOpcionRolService")
@Transactional
public class SeOpcionRolServiceImpl implements SeOpcionRolService {

    @Autowired
    SeOpcionRolRepository dao;

    @Override
    @ExceptionServiceAnnotation
    public SeOpcionRolDto save(SeOpcionRolDto dto) throws ServiceException {
        try {
            if (dto != null) {
                dao.save(dto);
            } else {
                throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
            }
        }catch (Exception e){
            throw (e);
        }

        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public SeOpcionRolDto getById(BigInteger id) throws ServiceException {

        SeOpcionRolDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }
        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<SeOpcionRolDto> gets(SeOpcionRolDto dto) throws ServiceException {
        List<SeOpcionRolDto> lista = null;
        if (dto != null) {
            lista = (List<SeOpcionRolDto>) dao.findAll();
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<SeOpcionRolDto> getByRol(BigInteger idRol) throws ServiceException {
        List<SeOpcionRolDto> lista = null;

        if (idRol != null) {
            lista = dao.getByRol(idRol);
        } else {
            throw (new ServiceException("El rol no puede ser nulo"));
        }
        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<SeOpcionRolDto> getActivosByRol(BigInteger idRol) throws ServiceException {
        List<SeOpcionRolDto> lista = null;

        if (idRol != null) {
            lista = dao.getByRolEstado(idRol, Boolean.TRUE);
        } else {
            throw (new ServiceException("El id de rol no puede ser nulo"));
        }
        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public SeOpcionRolDto getByRolOsNombre(BigInteger idRol, String osNombre) throws ServiceException {
        SeOpcionRolDto osSeOpcionRolDto = null;
        try {
            List<SeOpcionRolDto> lst = dao.getByRolOsNombre(idRol, osNombre);
            if(lst != null && !lst.isEmpty()){
                osSeOpcionRolDto = lst.get(0);
            }
        }catch (Exception e){
            throw (e);
        }

        return osSeOpcionRolDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public void estadoOpcionesPorRol(Boolean estado, BigInteger idRol)  throws ServiceException {
        try {
            dao.estadoOpcionesPorRol(estado, idRol);
        }catch (Exception e){
            throw (e);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public List<Object> opcionesParaMenu(BigInteger idRol) throws ServiceException{
        List<Object> lstRetorno = new ArrayList<>();
        try {
            Gson gson = new Gson();
            //Obtenemos los permisos del rol
            List<SeOpcionRolDto> lstPermisos = this.getActivosByRol(idRol);
            if(lstPermisos!=null && !lstPermisos.isEmpty()){
                List<SeOpcionDto> lstProcesado = new ArrayList<>();
                //Verificamos si todos los nodos padre se encuentran presentes en la lista
                for (SeOpcionRolDto or : lstPermisos){
                    if(or.getOpcionDto().getPadreDto()!=null)
                        verificarPadres(lstPermisos, lstProcesado, or.getOpcionDto());
                }

                if(lstProcesado!=null && !lstProcesado.isEmpty()) {
                    UtlGeneral.ordena(lstProcesado, "orden");
                    //En este punto deberiamos tener ya todas las opciones del menu listas para armar el arbol
                    List<SeOpcionDto> lstSoloPadres = obtenerSoloPadres(lstProcesado);
                    if(lstSoloPadres!= null && !lstSoloPadres.isEmpty()){
                        for(SeOpcionDto o : lstSoloPadres) {
                            int cantHijos = obtenerCantidadHijos(lstProcesado, o);
                            MenuWebDto menuWebDto = new MenuWebDto(o.getEtiqueta(), o.getIcon(), generarRouterLink(o));
                            recursividadArbol(menuWebDto, o, lstProcesado);
                            lstRetorno.add(menuWebDto);
                        }
                    }
                }
            }
        }catch (Exception e){
            throw e;
        }
        return lstRetorno;
    }

    private void recursividadArbol(MenuWebDto menuWebDto, SeOpcionDto padreDto, List<SeOpcionDto> lstProcesado){
        List<SeOpcionDto> lstHijos = obtenerHijos(lstProcesado, padreDto);
        for(SeOpcionDto o : lstHijos){
            List<SeOpcionDto> lstHijos2 = obtenerHijos(lstProcesado, o);
            if(lstHijos2!=null && !lstHijos2.isEmpty()){
                MenuWebDto tDto = new MenuWebDto(o.getEtiqueta(), o.getIcon(), generarRouterLink(o));
                recursividadArbol(tDto, o, lstProcesado);
                if(menuWebDto.getItems()==null)
                    menuWebDto.setItems(new ArrayList<>());
                menuWebDto.getItems().add(tDto);
            }else{
                MenuWebDto tDto = new MenuWebDto(o.getEtiqueta(), o.getIcon(), generarRouterLink(o));
                if(menuWebDto.getItems()==null)
                    menuWebDto.setItems(new ArrayList<>());
                menuWebDto.getItems().add(tDto);
            }
        }
    }

    private List<String> generarRouterLink(SeOpcionDto os){
        List<String> lst = null;
        if(os.getAction()!=null){
            lst = new ArrayList<>();
            lst.add(os.getAction());
        }
        return lst;
    }

    /**
     * Este metodo se va encargar de obtener siempre los padres de los hijos seleccionados, aun cuando el padre no lo este
     * @param lstPermisos
     * @param lstProcesado
     * @param os
     */
    private void verificarPadres(List<SeOpcionRolDto> lstPermisos, List<SeOpcionDto> lstProcesado, SeOpcionDto os){
        try {
            if(os!=null) {
                if (os.getId().equals(BigInteger.ZERO)) {
                    if (!verificarExistencia(lstProcesado, os)) {
                        lstProcesado.add(os);
                    }
                } else {
                    lstProcesado.add(os);
                    if (!verificarExistencia(lstProcesado, os.getPadreDto()))
                        verificarPadres(lstPermisos, lstProcesado, os.getPadreDto());
                }
            }
        }catch(Exception e){
            //System.out.println("ERROR : " + e.getMessage());
        }
    }

    private List<SeOpcionDto> obtenerSoloPadres(List<SeOpcionDto> lstProcesado){
        List<SeOpcionDto> lstRetorno = new ArrayList<>();
        for(SeOpcionDto os : lstProcesado){
            if(os.getPadreDto()!=null && os.getPadreDto().getId().equals(BigInteger.ZERO))
                lstRetorno.add(os);
        }
        return lstRetorno;
    }

    private int obtenerCantidadHijos(List<SeOpcionDto> lstProcesado, SeOpcionDto os){
        int cont = 0;
        for(SeOpcionDto os1 : lstProcesado){
            if(os1.getPadreDto()!=null && os1.getPadreDto().getId().equals(os.getId()))
                cont ++;
        }
        return cont;
    }

    private List<SeOpcionDto> obtenerHijos(List<SeOpcionDto> lstProcesado, SeOpcionDto os){
        List<SeOpcionDto>  lstHijos = new ArrayList<>();
        for(SeOpcionDto os1 : lstProcesado){
            if(os1.getPadreDto()!=null && os1.getPadreDto().getId().equals(os.getId()))
                lstHijos.add(os1);
        }
        return lstHijos;
    }

    private boolean verificarExistencia(List<SeOpcionDto> lstProcesado, SeOpcionDto or){
        boolean esta = false;
        if(or!=null) {
            for (SeOpcionDto os1 : lstProcesado) {
                if (os1.getId().equals(or.getId()))
                    esta = true;
            }
        }
        return esta;
    }
}
