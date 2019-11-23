package com.misernandfriends.cinemaclub.dao.review;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.review.CommentDTO;
import com.misernandfriends.cinemaclub.repository.review.CommentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class CommentDAOImpl extends AbstractDAOImpl<CommentDTO> implements CommentRepository {
    @Override
    protected Class<CommentDTO> getEntityClazz() {
        return CommentDTO.class;
    }

    @Override
    public List<CommentDTO> getUserComments(Long userId) {
        return getUserComments(userId, 20);
    }

    @Override
    public List<CommentDTO> getUserComments(Long userId, int maxResults) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.infoCU.id = :userId AND data.infoRD IS NULL";
        TypedQuery<CommentDTO> query = em.createQuery(queryTxt, CommentDTO.class)
                .setParameter("userId", userId)
                .setMaxResults(maxResults);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
