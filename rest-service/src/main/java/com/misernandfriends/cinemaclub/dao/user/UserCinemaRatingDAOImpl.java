package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.model.user.UserCinemaRatingDTO;
import com.misernandfriends.cinemaclub.repository.user.UserCinemaRatingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserCinemaRatingDAOImpl extends UserRatingDAOImpl<UserCinemaRatingDTO> implements UserCinemaRatingRepository {

    @Override
    protected Class<UserCinemaRatingDTO> getEntityClazz() {
        return UserCinemaRatingDTO.class;
    }

    @Override
    public Optional<UserCinemaRatingDTO> getByUser(Long userId, String referenceValue) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId AND data.cinema.id = :referenceValue ";
        TypedQuery<UserCinemaRatingDTO> query = em.createQuery(queryTxt, getEntityClazz())
                .setParameter("userId", userId)
                .setParameter("referenceValue", Long.parseLong(referenceValue));
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
