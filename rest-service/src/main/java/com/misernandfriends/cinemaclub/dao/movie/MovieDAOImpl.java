package com.misernandfriends.cinemaclub.dao.movie;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class MovieDAOImpl extends AbstractDAOImpl<MovieDTO> implements MovieRepository {

    @Override
    protected Class<MovieDTO> getEntityClazz() {
        return MovieDTO.class;
    }

    @Override
    public Optional<MovieDTO> getByApiUrl(String apiUrl) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.apiUrl = :apiUrl AND data.infoRD IS NULL";
        TypedQuery<MovieDTO> query = em.createQuery(queryTxt, MovieDTO.class)
                .setParameter("apiUrl", apiUrl);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MovieDTO> getByTitle(String title) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.title = :title AND data.infoRD IS NULL";
        TypedQuery<MovieDTO> query = em.createQuery(queryTxt, MovieDTO.class)
                .setParameter("title", title);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
