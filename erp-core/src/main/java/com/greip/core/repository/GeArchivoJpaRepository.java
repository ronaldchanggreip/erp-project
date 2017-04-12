package com.greip.core.repository;

import com.greip.core.dto.GeArchivoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * Created by HvivesO on 05/01/2017.
 */
@Repository
public interface GeArchivoJpaRepository extends JpaRepository<GeArchivoDto, BigInteger>, GeArchivoJpaRepositoryCustom {
}
