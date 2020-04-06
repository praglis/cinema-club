package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.RecommendationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

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
    public List<Long> getSimilarUser(List<String> moviesUrl, Long exceptUserId) {
        String queryTxt = "SELECT data.user.id, COUNT(data.movie.id) FROM FavoriteMovieDTO data " +
                "WHERE data.user.id <> :withoutUser AND data.movie.apiUrl IN (:moviesUrls) " +
                "GROUP BY data.user.id " +
                "ORDER BY COUNT(data.movie.id) DESC";
        Query query = em.createQuery(queryTxt)
                .setParameter("moviesUrls", moviesUrl)
                .setParameter("withoutUser", exceptUserId)
                .setMaxResults(5);
        List<Object[]> resultList = query.getResultList();
        List<Long> users = new ArrayList<>();
        for (Object[] o : resultList) {
            users.add((Long) o[1]);
        }
        return users;
    }
}
