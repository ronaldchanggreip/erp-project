package com.greip.core.repository;

import com.greip.core.dto.GeUbigeoDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeUbigeoRepository extends CrudRepository<GeUbigeoDto, BigInteger> ,  BaseRepository<GeUbigeoDto, BigInteger>{

    @Query("select dto from GeUbigeoDto dto where dto.padreDto.id = ?1 and dto.estado = ?2")
    public List<GeUbigeoDto> getByActivosPadre(BigInteger idPadre, Boolean estado);

    @Query("select dto from GeUbigeoDto dto where dto.padreDto.id = ?1")
    public List<GeUbigeoDto> getByPadre(BigInteger idPadre);


}
