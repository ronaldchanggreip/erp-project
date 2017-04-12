package com.greip.core.repository;

import com.greip.core.dto.GeParametroDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeParametroRepository extends CrudRepository<GeParametroDto, BigInteger>,  BaseRepository<GeParametroDto, BigInteger> {

    @Query("select dto from GeParametroDto dto where dto.grupoDto.id = ?1 and dto.estado = ?2 order by dto.descripcion asc")
    public List<GeParametroDto> getByGrupoEstado(BigInteger idGrupo, Boolean estado);

    @Query("select dto from GeParametroDto dto where dto.grupoDto.id = ?1")
    public List<GeParametroDto> getByGrupo(BigInteger idGrupo);

}
