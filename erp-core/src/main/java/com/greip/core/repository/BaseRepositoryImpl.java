package com.greip.core.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by HvivesO on 06/01/2017.
 */
public class BaseRepositoryImpl<Dto, ID extends Serializable> extends SimpleJpaRepository<Dto, ID> implements  BaseRepository<Dto, ID>{

    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<Dto, ID> entityInformation, EntityManager entityManager){
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dto> ejecutarQuery(String query, List<Object> params){
        List<Dto> lst = null;

        Query hQuery = entityManager.createQuery(query);

        if(!params.isEmpty()){
            for (int i = 0; i < params.size(); i++) {
                //System.out.println("PARAM : " + params.get(i));
                hQuery.setParameter(i + 1, params.get(i));
            }
        }
        lst = (List<Dto>)hQuery.getResultList();
        //System.out.println("QUERY  : " + query);

        return lst;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dto> ejecutarQuery(String query, List<Object> params, boolean limit, int first, int max){
        List<Dto> lst = null;

        Query hQuery = entityManager.createQuery(query);

        if(!params.isEmpty()){
            for (int i = 0; i < params.size(); i++) {
                hQuery.setParameter(i + 1, params.get(i));
                //System.out.println("PARAM : " + params.get(i));
            }
        }
        if(limit)
            lst = (List<Dto>)hQuery.setFirstResult(first).setMaxResults(max).getResultList();
        else
            lst = (List<Dto>)hQuery.getResultList();

        return lst;
    }
}
