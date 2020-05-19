package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.repository.user.UserRatingRepository;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public abstract class UserRatingDAOImpl<T extends Serializable> extends AbstractDAOImpl<T> implements UserRatingRepository<T> {

    @Override
    public List<T> getUserBestRated(Long userId, int maxResult) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId " +
                "ORDER BY data.rating DESC";
        TypedQuery<T> query = em.createQuery(queryTxt, getEntityClazz())
                .setMaxResults(maxResult)
                .setParameter("userId", userId);
        return query.getResultList();
    }
}
