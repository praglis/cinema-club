package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.BadgeDTO;
import com.misernandfriends.cinemaclub.repository.user.BadgeRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class BadgeDAOImpl extends AbstractDAOImpl<BadgeDTO> implements BadgeRepository {
    @Override
    protected Class<BadgeDTO> getEntityClazz() {
        return BadgeDTO.class;
    }

    @Override
    public Optional<BadgeDTO> findBadgeFromValue(int value) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE :value BETWEEN data.valueFrom AND data.valueTo";
        TypedQuery<BadgeDTO> query = em.createQuery(queryTxt, BadgeDTO.class)
                .setParameter("value", value);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
