package com.greip.core.repository;

//import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;

/**
 * Created by mdp_changr on 11/11/2016.
 */
public class GeRedisRepositoryImpl implements GeRedisRepository {
    @Override
    public void add(String key, String value) {

    }

    @Override
    public void send(String key, String value) {

    }

    @Override
    public String getBy(String key) {
        return null;
    }

    @Override
    public Set<String> getKeys(String patternKey) {
        return null;
    }

    @Override
    public Set<String> getAllValuesBy(String patternKey) {
        return null;
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void flushDb() {

    }

    /*private final StringRedisTemplate template;

    public GeRedisRepositoryImpl(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public void add(String key, String value) {
        this.template.opsForValue().set(key, value);
    }

    @Override
    public void send(String key, String value) {
        this.template.convertAndSend(key, value);
    }

    @Override
    public String getBy(String key) {
        return this.template.opsForValue().get(key);
    }

    @Override
    public Set<String> getKeys(String patternKey) {
        return this.template.keys(patternKey);
    }

    @Override
    public Set<String> getAllValuesBy(String patternKey) {
        final Set<String> keys = getKeys(patternKey);
        final Set<String> values = new HashSet<String>(keys.size());

        for (String key : keys) {
            values.add(getBy(key));
        }

        return values;
    }

    @Override
    public void delete(String key) {
        this.template.opsForValue().getOperations().delete(key);
    }

    @Override
    public void flushDb() {
        this.template.getConnectionFactory().getConnection().flushDb();
    }

    */
}
