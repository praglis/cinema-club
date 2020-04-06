package com.misernandfriends.cinemaclub.dao;

import com.misernandfriends.cinemaclub.repository.AbstractRepository;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        setCurrentDate(entity, "setInfoCD");
        em.persist(entity);
        return entity;
    }

    private void setCurrentDate(T entity, String methodName) {
        Method setInfoCD;
        try {
            setInfoCD = entity.getClass().getMethod(methodName, Date.class);
        } catch (NoSuchMethodException e) {
            return;
        }
        try {
            setInfoCD.invoke(entity, DateTimeUtil.getCurrentDate());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public T update(T entity) {
        setCurrentDate(entity, "setInfoMD");
        return em.merge(entity);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        em.remove(em.merge(entity));
    }

    @Override
    public Optional<T> getById(long id) {
        return Optional.ofNullable(em.find(getEntityClazz(), id));
    }

    @Override
    public List<T> getAll() {
        String query = "SELECT data FROM " + getEntityName() + " data";
        return em.createQuery(query, getEntityClazz()).getResultList();
    }

    protected abstract Class<T> getEntityClazz();

    @Override
    public void flush() {
        em.flush();
    }
}
