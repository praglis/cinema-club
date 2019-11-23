package com.misernandfriends.cinemaclub.dao.review;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.repository.review.UserReviewRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class UserReviewDAOImpl extends AbstractDAOImpl<UserReviewDTO> implements UserReviewRepository {
    @Override
    protected Class<UserReviewDTO> getEntityClazz() {
        return UserReviewDTO.class;
    }

    @Override
    public List<UserReviewDTO> getUserReviews(Long userId) {
        return null;
    }

    @Override
    public List<UserReviewDTO> getUserReviews(Long userId, int maxResults) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.infoCU.id = :userId AND data.infoRD IS NULL";
        TypedQuery<UserReviewDTO> query = em.createQuery(queryTxt, UserReviewDTO.class)
                .setParameter("userId", userId)
                .setMaxResults(maxResults);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
