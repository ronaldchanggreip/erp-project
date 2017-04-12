package com.greip.core.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HvivesO on 06/01/2017.
 */
@NoRepositoryBean
public interface BaseRepository<Dto,ID extends Serializable> extends Repository<Dto, ID>{

    public List<Dto> ejecutarQuery(String query, List<Object> params);
    public List<Dto> ejecutarQuery(String query, List<Object> params, boolean limit, int first, int max);
}
