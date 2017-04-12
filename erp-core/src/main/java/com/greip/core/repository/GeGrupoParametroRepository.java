package com.greip.core.repository;

import com.greip.core.dto.GeGrupoParametroDto;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeGrupoParametroRepository extends CrudRepository<GeGrupoParametroDto, BigInteger> ,  BaseRepository<GeGrupoParametroDto, BigInteger>{


}
