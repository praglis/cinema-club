package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.repository.user.VerificationTokenRepository;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.Optional;

@Repository
public class VerificationTokenDAOImpl extends AbstractDAOImpl<VerificationTokenDTO> implements VerificationTokenRepository {
    @Override
    protected Class<VerificationTokenDTO> getEntityClazz() {
        return VerificationTokenDTO.class;
    }

    @Override
    public Optional<VerificationTokenDTO> getByUserId(Long userId, String type) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId AND data.tokenType = :type AND data.infoRD IS NULL";
        TypedQuery<VerificationTokenDTO> query = em.createQuery(queryTxt, VerificationTokenDTO.class)
                .setParameter("userId", userId)
                .setParameter("type", type);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void setAsUsed(Long id) {
        Date currDate = DateTimeUtil.getCurrentDate();
        String queryTxt = "UPDATE " + getEntityName() + " data SET data.infoRD = :date WHERE " +
                "data.user.id = :id";

        em.createQuery(queryTxt)
                .setParameter("date", currDate)
                .setParameter("id", id).executeUpdate();
    }
}
