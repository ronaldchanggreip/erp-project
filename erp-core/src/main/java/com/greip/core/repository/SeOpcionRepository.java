package com.greip.core.repository;

import com.greip.core.dto.SeOpcionDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface SeOpcionRepository extends CrudRepository<SeOpcionDto, BigInteger> {

    @Query("select dto from SeOpcionDto dto where dto.padreDto.id = ?1 and dto.estado = ?2")
    public List<SeOpcionDto> getByPadreEstado(BigInteger idPadre, Boolean estado);

    @Query("select dto from SeOpcionDto dto where dto.padreDto.id = ?1")
    public List<SeOpcionDto> getByPadre(BigInteger idPadre);

    @Query("select dto from SeOpcionDto dto where dto.nombre = ?1")
    public List<SeOpcionDto> getByNombre(String nombre);

    @Query("select count(dto) from SeOpcionDto dto where dto.padreDto.id = ?1 and dto.estado = ?2")
    public Long cantidadHijosActivos(BigInteger idPadre, Boolean estado);
}
