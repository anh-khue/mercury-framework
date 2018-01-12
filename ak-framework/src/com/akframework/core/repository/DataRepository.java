package com.akframework.core.repository;

import java.util.List;
import java.util.Optional;

public interface DataRepository<T> extends Repository {

    public List<T> findAll();

    public Optional<T> findById(int... id);

    public void save(T t);

    public void remove(int... id);
}
