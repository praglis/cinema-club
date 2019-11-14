package com.misernandfriends.cinemaclub.repository;


import java.io.Serializable;

public interface AbstractRepository<T extends Serializable> {

    public T create(T entity);

    public T update(T entity);

    public void delete(T entity);

    public T getById(long id);

}
