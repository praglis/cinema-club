package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.UserRatingDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRatingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class UserRatingDAOImpl extends AbstractDAOImpl<UserRatingDTO> implements UserRatingRepository {
    @Override
    protected Class<UserRatingDTO> getEntityClazz() {
        return UserRatingDTO.class;
    }

    @Override
    public double getAvgRatingForMovie(Long movieId) {
        String queryTxt = "SELECT AVG(data.rating) FROM " + getEntityName() + " data WHERE " +
                "data.movie.id = :movieId";
        TypedQuery<Double> query = em.createQuery(queryTxt, Double.class)
                .setParameter("movieId", movieId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0D;
        }
    }
}
