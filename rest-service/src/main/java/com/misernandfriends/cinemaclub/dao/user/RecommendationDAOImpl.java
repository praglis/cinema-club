package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.repository.user.RecommendationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecommendationDAOImpl extends AbstractDAOImpl<RecommendationDTO> implements RecommendationRepository {

    @Override
    protected Class<RecommendationDTO> getEntityClazz() {
        return RecommendationDTO.class;
    }

    @Override
    public RecommendationDTO get(Long userId, String type, String value) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.userId = :userId AND data.type = :type AND data.value = :value";
        TypedQuery<RecommendationDTO> query = em.createQuery(queryTxt, RecommendationDTO.class)
                .setParameter("userId", userId)
                .setParameter("type", type)
                .setParameter("value", value);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<RecommendationDTO> getByUser(Long userId, String type) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.userId = :userId AND data.type = :type";
        return em.createQuery(queryTxt, RecommendationDTO.class)
                .setParameter("userId", userId)
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<RecommendationDTO> getRecommendation(Long userId, String type, int maxResult) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.userId = :userId AND data.type = :type " +
                "ORDER BY data.fitLevel DESC";
        return em.createQuery(queryTxt, RecommendationDTO.class)
                .setParameter("userId", userId)
                .setParameter("type", type)
                .setMaxResults(maxResult)
                .getResultList();
    }

    @Override
    public Map<Long, Integer> getSimilarUser(List<String> moviesUrl, Long exceptUserId) {
        String queryTxt = "SELECT data.user.id, COUNT(data.movie.id) FROM FavoriteMovieDTO data " +
                "WHERE data.user.id <> :withoutUser AND data.movie.apiUrl IN (:moviesUrls) " +
                "GROUP BY data.user.id " +
                "ORDER BY COUNT(data.movie.id) DESC";
        TypedQuery<Object[]> query = em.createQuery(queryTxt, Object[].class)
                .setParameter("moviesUrls", moviesUrl)
                .setParameter("withoutUser", exceptUserId)
                .setMaxResults(5);
        List<Object[]> resultList = query.getResultList();
        HashMap<Long, Integer> values = new HashMap<>();
        for (Object[] o : resultList) {
            values.put((Long) o[1], (Integer) o[0]);
        }
        return values;
    }

    @Override
    public List<MovieDTO> findBestMoviesForBy(Long id, Long userId, int moviesToAdd) {
        String queryTxt = "SELECT data.movie FROM UserRatingDTO data " +
                "WHERE data.user.id IN :userId AND data.movie.id NOT IN (SELECT fav.movie.id FROM FavoriteMovieDTO fav " +
                "WHERE fav.user.id = :masterUser) " +
                "ORDER BY data.rating DESC";
        TypedQuery<MovieDTO> query = em.createQuery(queryTxt, MovieDTO.class)
                .setMaxResults(moviesToAdd)
                .setParameter("userId", userId)
                .setParameter("masterUser", moviesToAdd);
        return null;
    }
}
