package com.greip.core.repository;

import com.greip.core.dto.SeUsuarioRolDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface SeUsuarioRolRepository extends CrudRepository<SeUsuarioRolDto, BigInteger> {

    @Query("select dto from SeUsuarioRolDto dto where dto.usuarioDto.id = ?1")
    public List<SeUsuarioRolDto> getByUsuario(BigInteger idUsuario);

    @Query("select dto from SeUsuarioRolDto dto where dto.rolDto.id = ?1")
    public List<SeUsuarioRolDto> getByRol(BigInteger idRol);

    @Query("select dto from SeUsuarioRolDto dto where dto.rolDto.id = ?1 and dto.usuarioDto.id = ?2")
    public List<SeUsuarioRolDto> getByRolUsuario(BigInteger idRol, BigInteger idUsuario);


}
