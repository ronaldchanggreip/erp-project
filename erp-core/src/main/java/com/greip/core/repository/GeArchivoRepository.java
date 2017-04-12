package com.greip.core.repository;

        import com.greip.core.dto.GeArchivoDto;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;

        import java.math.BigInteger;
        import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeArchivoRepository extends CrudRepository<GeArchivoDto, BigInteger> ,  BaseRepository<GeArchivoDto, BigInteger>{


    @Query("select dto from GeArchivoDto dto where dto.entidadDto.id = ?1 and dto.registro = ?2")
    public List<GeArchivoDto> getByEntidadRegistro(BigInteger idEntidad, BigInteger idRegistro);
}
