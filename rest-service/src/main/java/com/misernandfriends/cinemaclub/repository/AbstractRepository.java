package com.misernandfriends.cinemaclub.repository;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T extends Serializable> {
    T create(T entity);
    T update(T entity);
    void delete(T entity);
    Optional<T> getById(long id);
    List<T> getAll();
}
