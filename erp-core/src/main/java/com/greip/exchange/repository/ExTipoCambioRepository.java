package com.greip.exchange.repository;

import com.greip.core.repository.BaseRepository;
import com.greip.exchange.dto.ExTipoCambioDto;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface ExTipoCambioRepository extends CrudRepository<ExTipoCambioDto, BigInteger>, BaseRepository<ExTipoCambioDto, BigInteger>, ExTipoCambioRepositoryCustom{

    String formato = "YYYYMMDD";


    //@Query("select dto from ExTipoCambioDto dto where UtlDate.formatearFechaCompare(dto.fechaVigencia)=UtlDate.formatearFechaCompare(?1)")
    //public List<ExTipoCambioDto> getsPorFecha(Date fecha);
}
