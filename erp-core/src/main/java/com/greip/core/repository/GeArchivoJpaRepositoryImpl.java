package com.greip.core.repository;

import com.greip.core.dto.GeArchivoDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by mdp_changr on 11/11/2016.
 */
@Repository("GeArchivoJpaRepositoryCustom")
public class GeArchivoJpaRepositoryImpl implements GeArchivoJpaRepositoryCustom {

    Class clazz = GeArchivoDto.class;

    /*@Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeArchivoDto> gets(String nombre) {
        StringBuilder sQuery = new StringBuilder();

        sQuery.append("SELECT t \n");
        sQuery.append("FROM ");
        sQuery.append(clazz.getSimpleName());
        sQuery.append(" t");

        Query query = getSessionFactory().getCurrentSession().createQuery(sQuery.toString());

        List<GeArchivoDto> entidades = (List<GeArchivoDto>) query.list();

        return entidades;
    }*/

    @PersistenceContext
    private EntityManager entityManager;
    public List<GeArchivoDto> gets(String nombre) {
        StringBuilder sQuery = new StringBuilder();

        sQuery.append("SELECT t \n");
        sQuery.append("FROM ");
        sQuery.append(clazz.getSimpleName());
        sQuery.append(" t");

        Query query = entityManager.createQuery(sQuery.toString());

        List<GeArchivoDto> entidades = (List<GeArchivoDto>) query.getResultList();

        return entidades;
    }

}
