package com.greip.core.repository;

import com.greip.core.dto.SeOpcionRolDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface SeOpcionRolRepository extends CrudRepository<SeOpcionRolDto, BigInteger> {

    @Query("select dto from SeOpcionRolDto dto where dto.rolDto.id = ?1")
    public List<SeOpcionRolDto> getByRol(BigInteger idRol);

    @Query("select dto from SeOpcionRolDto dto where dto.rolDto.id = ?1 and dto.estado = ?2")
    public List<SeOpcionRolDto> getByRolEstado(BigInteger idRol, Boolean estado);

    @Query("select dto from SeOpcionRolDto dto where dto.rolDto.id = ?1 and dto.opcionDto.nombre = ?2")
    public List<SeOpcionRolDto> getByRolOsNombre(BigInteger rol, String osNombre);

    @Modifying
    @Query("update SeOpcionRolDto dto set dto.estado = ?1 where dto.rolDto.id = ?2")
    public void estadoOpcionesPorRol(Boolean estado, BigInteger idRol);
}
