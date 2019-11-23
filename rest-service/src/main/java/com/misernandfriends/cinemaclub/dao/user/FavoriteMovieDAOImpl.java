package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.CategoryDTO;
import com.misernandfriends.cinemaclub.model.user.FavoriteMovieDTO;
import com.misernandfriends.cinemaclub.repository.user.FavoriteMovieRepository;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class FavoriteMovieDAOImpl extends AbstractDAOImpl<FavoriteMovieDTO> implements FavoriteMovieRepository {

    @Override
    protected Class<FavoriteMovieDTO> getEntityClazz() {
        return FavoriteMovieDTO.class;
    }

    @Override
    public List<MovieDTO> getMovieForCategory(Long categoryId, Long userId) {
        String queryTxt = "SELECT data.movie FROM " + getEntityName() + " data WHERE " +
                "data.category.id = :categoryId";
        if (userId != null) {
            queryTxt += " AND data.user.id = :userId";
        }

        TypedQuery<MovieDTO> query = em.createQuery(queryTxt, MovieDTO.class)
                .setParameter("categoryId", categoryId);
        if (userId != null) {
            query.setParameter("userId", userId);
        }
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Lists.emptyList();
        }
    }

    @Override
    public List<MovieDTO> getMovieForCategory(Long categoryId) {
        return getMovieForCategory(categoryId, null);
    }

    @Override
    public Optional<FavoriteMovieDTO> getFavoriteMovie(Long categoryId, Long userId, Long movieId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId AND data.movie.id = :movieId AND data.category.id = :categoryId";

        TypedQuery<FavoriteMovieDTO> query = em.createQuery(queryTxt, FavoriteMovieDTO.class)
                .setParameter("categoryId", categoryId)
                .setParameter("userId", userId)
                .setParameter("movieId", movieId);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CategoryDTO> getMovieCategory(Long userId, Long movieId) {
        String queryTxt = "SELECT data.category FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId AND data.movie.id = :movieId";
        TypedQuery<CategoryDTO> query = em.createQuery(queryTxt, CategoryDTO.class)
                .setParameter("userId", userId)
                .setParameter("movieId", movieId);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
