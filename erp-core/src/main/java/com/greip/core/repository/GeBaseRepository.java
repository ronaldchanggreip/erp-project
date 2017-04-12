package com.greip.core.repository;

import com.greip.core.annotation.ExceptionServiceAnnotation;
import com.greip.core.exception.RepositoryException;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * Created by ronaldchang on 19/11/16.
 */

@NoRepositoryBean
public interface GeBaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    @ExceptionServiceAnnotation
    <S extends T> S save(S var1) throws RepositoryException;

    <S extends T> Iterable<S> save(Iterable<S> var1) throws RepositoryException;

    T findOne(ID var1) throws RepositoryException;

    boolean exists(ID var1) throws RepositoryException;

    Iterable<T> findAll() throws RepositoryException;

    Iterable<T> findAll(Iterable<ID> var1) throws RepositoryException;

    long count() throws RepositoryException;

    void delete(ID var1) throws RepositoryException;

    void delete(T var1) throws RepositoryException;

    void delete(Iterable<? extends T> var1) throws RepositoryException;

    void deleteAll() throws RepositoryException;


}
