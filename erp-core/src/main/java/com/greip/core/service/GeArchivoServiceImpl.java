package com.greip.core.service;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.dto.FiltroDto;
import com.greip.core.dto.FiltroTransDto;
import com.greip.core.dto.GeArchivoDto;
import com.greip.core.dto.GeEntidadDto;
import com.greip.core.repository.GeArchivoJpaRepository;
import com.greip.core.repository.GeArchivoRepository;
import com.greip.core.util.UtlCaracter;
import com.greip.core.util.UtlDate;
import com.greip.core.util.UtlFile;
import com.greip.core.dto.*;
import com.greip.core.exception.ServiceException;
import com.greip.core.util.UtlFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Service("geArchivoService")
@Transactional
public class GeArchivoServiceImpl implements GeArchivoService {

    @Autowired
    GeArchivoRepository dao;
    @Autowired
    GeArchivoJpaRepository daoCustom;
    @Autowired
    private Environment env;

    @Override
    @ExceptionServiceAnnotation
    public GeArchivoDto save(GeArchivoDto dto) throws ServiceException {
        if (dto != null) {
            dao.save(dto);
        } else {
            throw (new ServiceException(ServiceException.ENTIDAD_NOT_NULL));
        }

        return dto;
    }

    @Override
    @ExceptionServiceAnnotation
    public GeArchivoDto getById(BigInteger id) throws ServiceException {

        GeArchivoDto retornoDto = null;
        if (id != null) {
            retornoDto = dao.findOne(id);
        } else {
            throw (new ServiceException(ServiceException.ID_NOT_NULL));
        }


        return retornoDto;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeArchivoDto> gets(FiltroDto filtroDto) throws ServiceException {
        List<GeArchivoDto> lista = null;
        List<Object> lstParams = new ArrayList<>();
        try {
            FiltroTransDto filtroTransDto = UtlFiltro.generarCondicion(filtroDto, "dto");
            String query = "select dto from GeArchivoDto dto ";
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
    public List<GeArchivoDto> gets(BigInteger idEntidad, BigInteger idRegistro) throws ServiceException {
        List<GeArchivoDto> lista = null;

        if (idEntidad != null && idRegistro != null) {
            lista = dao.getByEntidadRegistro(idEntidad, idRegistro);
        } else {
            throw (new ServiceException("La entidad y el registro no deben ser nulos"));
        }
        return lista;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeArchivoDto> guardarArchivos(MultipartFile[] files, BigInteger entidad, BigInteger registro) throws ServiceException {
        List<GeArchivoDto> lst = new ArrayList<>();
        GeArchivoDto dto = null;
        Date fecha = UtlDate.obtenerFechaActualDate();
        try {
            String rutaArchivos = env.getProperty("path.application") + "archivos" + File.separator + entidad.toString() + File.separator + registro.toString();
            File dir = new File(rutaArchivos );
            if (!dir.exists())
                dir.mkdirs();
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];

                byte[] bytes = file.getBytes();

                dto = new GeArchivoDto();
                dto.setBites(bytes);
                dto.setContentType(file.getContentType());
                dto.setEntidadDto(new GeEntidadDto(entidad));
                dto.setRegistro(registro);
                dto.setFecha(fecha);
                dto.setFechaCreacion(fecha);
                dto.setNombre(file.getOriginalFilename());
                dto.setEliminado(Boolean.FALSE);
                if (dto.getNombre().indexOf(".") != -1) {
                    dto.setExtension(dto.getNombre().substring(dto.getNombre().lastIndexOf(".") + 1, dto.getNombre().length()));
                }
                dto.setPeso(file.getSize());
                dto.setEstado(Boolean.TRUE);
                dto.setIndEsAtributo(Boolean.FALSE);
                lst.add(dto);
            }
            grabarArchivoVarios(lst, rutaArchivos);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            throw (new ServiceException(e.getMessage()));
        }
        return lst;
    }

    @Override
    @ExceptionServiceAnnotation
    public List<GeArchivoDto> modificarVarios(List<GeArchivoDto> lst) throws ServiceException {
        try{
            for(GeArchivoDto a : lst){
                save(a);
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            throw (new ServiceException(e.getMessage()));
        }
        return lst;
    }

    @Override
    @ExceptionServiceAnnotation
    public void eliminarVarios(List<GeArchivoDto> lst) throws ServiceException {
        try{
            for(GeArchivoDto a : lst){
                dao.delete(a);
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            throw (new ServiceException(e.getMessage()));
        }
    }

    private void grabarArchivoVarios(List<GeArchivoDto> lst, String rutaArchivos) throws FileNotFoundException, IOException, ServiceException {
        for (GeArchivoDto a : lst) {
            /*a.setUsuarioDto(sessionMBean.getSessionUsuarioDto());
            a.setTerminal(sessionMBean.getSessionTerminal());
            a.setFecha(UtilCore.Fecha.obtenerFechaActualDate());*/
            if (a.getId() == null) {
                save(a);
                //Renombramos el archivo una vez generado el identificador del mismo
                String nombre = UtlCaracter.generarNombreArchivo(a.getEntidadDto().getId(), a.getRegistro(), a.getId(), a.getNombre());
                a.setDetalle(nombre);
                a.setNombre(a.getNombre().substring(0, a.getNombre().lastIndexOf(".")));
                //Guardamos fisicamente el archivo
                String ruta = rutaArchivos + File.separator + nombre;
                a.setRuta(ruta);
                save(a);
                File f = new File(ruta);
                UtlFile.copiarArchivoHD(f, a.getBites());
            }
        }
    }
}
