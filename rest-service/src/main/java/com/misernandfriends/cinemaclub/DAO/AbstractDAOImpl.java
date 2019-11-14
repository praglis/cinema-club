package com.misernandfriends.cinemaclub.DAO;

import com.misernandfriends.cinemaclub.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

@Repository
public abstract class AbstractDAOImpl<T extends Serializable> implements AbstractRepository<T> {

    @PersistenceContext
    protected EntityManager em;

    public String getEntityName() {
        return getEntityClazz().getName();
    }

    @Transactional
    @Override
    public T create(T entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

    @Override
    public T getById(long id) {
        return em.find(getEntityClazz(), id);
    }

    protected abstract Class<T> getEntityClazz();

}
