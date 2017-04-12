package com.greip.core.repository;

import com.greip.core.dto.GeMonedaDto;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeMonedaRepository extends CrudRepository<GeMonedaDto, BigInteger> ,  BaseRepository<GeMonedaDto, BigInteger>{


}
