package com.greip.core.repository;

import com.greip.core.dto.GeEntidadDto;
import com.greip.core.exception.ServiceException;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeEntidadRepository extends GeBaseRepository<GeEntidadDto, BigInteger> {

    @Query("select dto from GeEntidadDto dto where dto.modulo = ?1")
    public List<GeEntidadDto> getByModulo(String modulo) throws ServiceException;


}
