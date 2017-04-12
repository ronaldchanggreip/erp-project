package com.greip.core.repository;

import com.greip.core.dto.GeLogErrorDto;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeLogErrorRepository extends CrudRepository<GeLogErrorDto, BigInteger> {


}
