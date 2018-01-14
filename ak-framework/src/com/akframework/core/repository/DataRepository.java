package com.akframework.core.repository;

import com.akframework.core.data.common.Entity;

import java.util.List;
import java.util.Optional;

public interface DataRepository<T extends Entity> extends Repository {

    List<T> findAll();

    Optional<T> findById(int... id);

    void save(T entity);

    void remove(int... id);
}
