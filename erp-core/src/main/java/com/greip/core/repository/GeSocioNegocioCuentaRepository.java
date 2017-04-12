package com.greip.core.repository;

import com.greip.core.dto.GeSocioNegocioCuentaDto;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface GeSocioNegocioCuentaRepository extends CrudRepository<GeSocioNegocioCuentaDto, BigInteger>, BaseRepository<GeSocioNegocioCuentaDto, BigInteger>{
}
