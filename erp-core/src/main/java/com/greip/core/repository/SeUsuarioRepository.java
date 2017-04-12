package com.greip.core.repository;

import com.greip.core.dto.SeUsuarioDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface SeUsuarioRepository extends CrudRepository<SeUsuarioDto, BigInteger> ,  BaseRepository<SeUsuarioDto, BigInteger>{

    @Query("select dto from SeUsuarioDto dto where upper(dto.login) = ?1 or upper(dto.email) = ?1")
    public List<SeUsuarioDto> getByLoginEmail(String nombreOemail);

    @Query("select dto from SeUsuarioDto dto where dto.id <> ?1 and (upper(dto.login) = ?2 or upper(dto.email) = ?2)")
    public List<SeUsuarioDto> getByLoginEmailExcepto(BigInteger id, String nombreOemail);
}
