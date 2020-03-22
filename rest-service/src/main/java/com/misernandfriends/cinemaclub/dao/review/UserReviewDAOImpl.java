package com.misernandfriends.cinemaclub.dao.review;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.review.UserReviewRepository;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<UserReviewDTO> getUserMovieReviews(String movieUrl) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.infoRD is NULL AND data.movie.apiUrl = :movieId";

        TypedQuery<UserReviewDTO> query = em.createQuery(queryTxt, UserReviewDTO.class)
                .setParameter("movieId", movieUrl);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(UserReviewDTO userReviewDTO, UserDTO user) {
        userReviewDTO.setInfoRU(user);
        userReviewDTO.setInfoRD(DateTimeUtil.getCurrentDate());
        update(userReviewDTO);
    }
}
