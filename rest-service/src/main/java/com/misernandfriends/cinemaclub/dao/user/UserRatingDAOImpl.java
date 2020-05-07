package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.UserRatingDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRatingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.List;

@Repository
public class UserRatingDAOImpl extends AbstractDAOImpl<UserRatingDTO> implements UserRatingRepository {
    @Override
    protected Class<UserRatingDTO> getEntityClazz() {
        return UserRatingDTO.class;
    }

    @Override
    public Optional<UserRatingDTO> getByUser(Long userId, String movieApi) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId AND data.movie.apiUrl = :movieId";
        TypedQuery<UserRatingDTO> query = em.createQuery(queryTxt, UserRatingDTO.class)
                .setParameter("userId", userId)
                .setParameter("movieId", movieApi);
        try {
            return Optional.of(query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public List<UserRatingDTO> getUserBestRatedMovies(Long userId, int maxResult) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId " +
                "ORDER BY data.rating DESC";
        TypedQuery<UserRatingDTO> query = em.createQuery(queryTxt, UserRatingDTO.class)
                .setMaxResults(maxResult)
                .setParameter("userId", userId);
        return query.getResultList();
    }
}
