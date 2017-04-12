package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.*;
import com.greip.core.repository.SeRolRepository;
import com.greip.core.dto.*;
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
@Service("seRolService")
@Transactional
public class SeRolServiceImpl implements SeRolService {

    @Autowired
    SeRolRepository dao;
    @Autowired
    SeOpcionRolService orService;
    @Autowired
    SeOpcionService osService;

    @Override
    @ExceptionServiceAnnotation
    public SeRolDto save(SeRolDto dto) throws ServiceException {
        try {
            if (dto != null) {
                dao.save(dto);

                //Desactivamos las opciones del rol
                orService.estadoOpcionesPorRol(Boolean.FALSE, dto.getId());
                //Obtenemos los permisos seleccionados
                String[] opciones = dto.getOpciones();
                if(opciones!=null && opciones.length>0){
                    for (String s : opciones){
                        //System.out.println("OPCION RECUPERADA : " + s + " - " + dto.getId());
                        SeOpcionRolDto orDto = orService.getByRolOsNombre(dto.getId(), s.trim());
                        if(orDto!=null){
                          //  System.out.println("OPCION RECUPERADA 2 : " + s + " - " + orDto.getId());
                            orDto.setEstado(Boolean.TRUE);
                            orService.save(orDto);
                        }
                    }
                }
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
    public void eliminarLogico(List<BigInteger> lst, AuditoriaDto auditoriaDto) throws ServiceException {
        for(BigInteger id : lst){
            SeRolDto dto = getById(id);
            dto.setFecha(auditoriaDto.getFecha());
            dto.setUsuario(auditoriaDto.getUsuarioDto().getId());
            dto.setTerminal(auditoriaDto.getTerminal());
            dto.setEliminado(Boolean.TRUE);
            dao.save(dto);
        }
    }

    @Override
    @ExceptionServiceAnnotation
    public SeRolDto getById(BigInteger id) throws ServiceException {

        SeRolDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
            if(retornoDto!=null){
                //Obtenemos los permisos activos del rol
                List<SeOpcionRolDto> lstOpciones = orService.getActivosByRol(retornoDto.getId());
                List<TreeWebDto> lstTree = null;
                if(lstOpciones!=null && !lstOpciones.isEmpty()){
                    lstTree = new ArrayList<>();
                    TreeWebDto treeWebDto = null;
                    for(SeOpcionRolDto sr : lstOpciones){
                        treeWebDto = new TreeWebDto(sr.getOpcionDto().getEtiqueta(), sr.getOpcionDto().getNombre(), sr.getOpcionDto().getIcon(), sr.getOpcionDto().getIcon());
                        lstTree.add(treeWebDto);
                    }
                }
                retornoDto.setTreeWebSelected(lstTree);
            }

        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<SeRolDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<SeRolDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from SeRolDto dto ";
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
    public List<SeRolDto> getsByNombre(String nombre) throws ServiceException {
        List<SeRolDto> lista = null;


        lista = dao.getsByNombre("%" + nombre.toUpperCase() + "%");

        return lista;
    }

}
