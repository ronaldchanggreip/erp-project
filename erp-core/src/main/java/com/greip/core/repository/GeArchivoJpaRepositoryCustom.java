package com.greip.core.repository;

import com.greip.core.dto.GeArchivoDto;

import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeArchivoJpaRepositoryCustom {

    public List<GeArchivoDto> gets(String nombre);
}
