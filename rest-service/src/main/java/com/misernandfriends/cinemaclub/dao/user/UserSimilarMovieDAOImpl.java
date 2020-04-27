package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserSimilarMovieDTO;
import com.misernandfriends.cinemaclub.repository.user.UserSimilarMovieRepository;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserSimilarMovieDAOImpl extends AbstractDAOImpl<UserSimilarMovieDTO> implements UserSimilarMovieRepository {
    @Override
    protected Class<UserSimilarMovieDTO> getEntityClazz() {
        return UserSimilarMovieDTO.class;
    }

    @Override
    @Transactional
    public List<MovieDTO> getForUser(Long userId) {
        Date date = DateTimeUtil.minusDate(DateTimeUtil.getCurrentDate(), 1, Calendar.DAY_OF_MONTH);
        String updateQuery = "DELETE " + getEntityName() + " WHERE infoCD < :date";
        em.createQuery(updateQuery)
                .setParameter("date", date)
                .executeUpdate();

        String queryTxt = "SELECT data.movie FROM " + getEntityName() + " data " +
                "WHERE data.user.id = :userId";
        TypedQuery<MovieDTO> query = em.createQuery(queryTxt, MovieDTO.class)
                .setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void clearForUser(Long userId) {
        String updateQuery = "DELETE " + getEntityName() + " WHERE user.id = :userId";
        em.createQuery(updateQuery)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
