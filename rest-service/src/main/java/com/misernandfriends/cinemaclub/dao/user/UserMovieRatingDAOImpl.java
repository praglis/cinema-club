package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.model.user.UserMovieRatingDTO;
import com.misernandfriends.cinemaclub.repository.user.UserMovieRatingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserMovieRatingDAOImpl extends UserRatingDAOImpl<UserMovieRatingDTO> implements UserMovieRatingRepository {
    @Override
    public Optional<UserMovieRatingDTO> getByUser(Long userId, String movieApi) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId AND data.movie.apiUrl = :movieId";
        TypedQuery<UserMovieRatingDTO> query = em.createQuery(queryTxt, getEntityClazz())
                .setParameter("userId", userId)
                .setParameter("movieId", movieApi);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    protected Class<UserMovieRatingDTO> getEntityClazz() {
        return UserMovieRatingDTO.class;
    }
}
