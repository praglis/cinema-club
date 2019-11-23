package com.misernandfriends.cinemaclub.dao.movie;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.PremiereDTO;
import com.misernandfriends.cinemaclub.repository.movie.PremiereRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class PremiereDAOImpl extends AbstractDAOImpl<PremiereDTO> implements PremiereRepository {
    @Override
    protected Class<PremiereDTO> getEntityClazz() {
        return PremiereDTO.class;
    }

    @Override
    public List<CinemaDTO> getCinemasForMovie(Long movieId) {
        String queryTxt = "SELECT data.cinema FROM " + getEntityName() + " data WHERE " +
                "data.cinema.id = :movieId";
        TypedQuery<CinemaDTO> query = em.createQuery(queryTxt, CinemaDTO.class)
                .setParameter("movieId", movieId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<MovieDTO> getMoviesForCinema(Long cinemaId) {
        String queryTxt = "SELECT data.movie FROM " + getEntityName() + " data WHERE " +
                "data.cinema.id = :cinemaId";
        TypedQuery<MovieDTO> query = em.createQuery(queryTxt, MovieDTO.class)
                .setParameter("cinemaId", cinemaId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
