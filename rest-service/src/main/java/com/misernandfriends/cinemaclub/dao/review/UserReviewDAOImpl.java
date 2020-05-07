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
import java.util.List;
import java.util.Optional;

@Repository
public class UserReviewDAOImpl extends AbstractDAOImpl<UserReviewDTO> implements UserReviewRepository {
    @Override
    protected Class<UserReviewDTO> getEntityClazz() {
        return UserReviewDTO.class;
    }

    @Override
    public List<UserReviewDTO> getUserMovieReviews(String movieUrl) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.infoRD is NULL AND data.movie.apiUrl = :movieId ORDER BY data.likes DESC, data.infoCD ";

        TypedQuery<UserReviewDTO> query = em.createQuery(queryTxt, UserReviewDTO.class)
                .setParameter("movieId", movieUrl);
        return query.getResultList();
    }

    @Override
    public Optional<UserReviewDTO> getUserReviewById(Long reviewId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.id = :reviewId";

        TypedQuery<UserReviewDTO> query = em.createQuery(queryTxt, UserReviewDTO.class)
                .setParameter("reviewId", reviewId);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void delete(UserReviewDTO userReviewDTO, UserDTO user) {
        userReviewDTO.setInfoRU(user);
        userReviewDTO.setInfoRD(DateTimeUtil.getCurrentDate());
        update(userReviewDTO);
    }
}
