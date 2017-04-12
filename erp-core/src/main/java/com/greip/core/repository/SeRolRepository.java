package com.greip.core.repository;

import com.greip.core.dto.SeRolDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface SeRolRepository extends CrudRepository<SeRolDto, BigInteger> ,  BaseRepository<SeRolDto, BigInteger>{

    @Query("select dto from SeRolDto dto where upper(dto.nombre) like ?1")
    public List<SeRolDto> getsByNombre(String nombre);

}
