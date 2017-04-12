package com.greip.core.repository;

import com.greip.core.dto.GeBitacoraDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeBitacoraRepository extends CrudRepository<GeBitacoraDto, BigInteger>,  BaseRepository<GeBitacoraDto, BigInteger> {

    @Query("Select dto from GeBitacoraDto dto where dto.entidadDto.id = ?1 and dto.registro = ?2")
    public List<GeBitacoraDto> getByEntidadRegistro(BigInteger idEntidad, BigInteger idRegistro);
}
