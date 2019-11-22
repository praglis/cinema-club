package com.misernandfriends.cinemaclub.DAO;

import com.misernandfriends.cinemaclub.repository.AbstractRepository;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
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
        fillInfoCd(entity);
        em.persist(entity);
        return entity;
    }

    private void fillInfoCd(T entity) {
        Method setInfoCD;
        try {
            setInfoCD = entity.getClass().getMethod("setInfoCD", Date.class);
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
        return em.merge(entity);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

    @Override
    public Optional<T> getById(long id) {
        return Optional.ofNullable(em.find(getEntityClazz(), id));
    }

    protected abstract Class<T> getEntityClazz();

}
