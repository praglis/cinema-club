package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.repository.user.RecommendationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
}
