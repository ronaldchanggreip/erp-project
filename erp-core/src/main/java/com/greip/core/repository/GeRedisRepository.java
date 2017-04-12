package com.greip.core.repository;

import java.util.Set;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public interface GeRedisRepository {

    public void add(String key, String value);

    public void send(String key, String value);

    public String getBy(String key);

    public Set<String> getKeys(String patternKey);

    public Set<String> getAllValuesBy(String patternKey);

    public void delete(String key);

    public void flushDb();


}
