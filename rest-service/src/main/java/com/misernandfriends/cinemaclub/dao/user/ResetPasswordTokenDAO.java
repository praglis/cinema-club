package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.ResetPasswordTokenDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.repository.user.ResetPasswordTokenRepository;
import com.misernandfriends.cinemaclub.repository.user.VerificationTokenRepository;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.Optional;

@Repository
public class ResetPasswordTokenDAO extends AbstractDAOImpl<ResetPasswordTokenDTO> implements ResetPasswordTokenRepository {
    @Override
    protected Class<ResetPasswordTokenDTO> getEntityClazz() {
        return ResetPasswordTokenDTO.class;
    }

    @Override
    public Optional<ResetPasswordTokenDTO> getByUserId(Long userId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId AND data.infoRD IS NULL";
        TypedQuery<ResetPasswordTokenDTO> query = em.createQuery(queryTxt, ResetPasswordTokenDTO.class)
                .setParameter("userId", userId);
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

