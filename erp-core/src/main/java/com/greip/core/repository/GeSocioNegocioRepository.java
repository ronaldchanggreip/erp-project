package com.greip.core.repository;

import com.greip.core.dto.GeSocioNegocioDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by HvivesO on 08/01/2017.
 */
public interface GeSocioNegocioRepository extends CrudRepository<GeSocioNegocioDto, BigInteger> ,  BaseRepository<GeSocioNegocioDto, BigInteger>{
    @Query("select dto from GeSocioNegocioDto dto where tipoDocumentoDto.id = ?1 and dto.numDocumento = ?2")
    public List<GeSocioNegocioDto> getByDocumento(BigInteger tipoDoc, String numDoc);
    @Query("select dto from GeSocioNegocioDto dto where dto.id <> ?1 and tipoDocumentoDto.id = ?2 and dto.numDocumento = ?3")
    public List<GeSocioNegocioDto> getByDocumentoExcepto(BigInteger id, BigInteger tipoDoc, String numDoc);
}
