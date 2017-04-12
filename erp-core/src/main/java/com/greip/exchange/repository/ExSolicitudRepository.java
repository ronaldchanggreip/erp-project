package com.greip.exchange.repository;

import com.greip.core.repository.BaseRepository;
import com.greip.exchange.dto.ExSolicitudDto;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface ExSolicitudRepository extends CrudRepository<ExSolicitudDto, BigInteger>, BaseRepository<ExSolicitudDto, BigInteger> {
}
