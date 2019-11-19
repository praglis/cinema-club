package com.misernandfriends.cinemaclub.DAO.event;

import com.misernandfriends.cinemaclub.DAO.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.event.NotificationDTO;
import com.misernandfriends.cinemaclub.repository.event.NotificationRepository;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class NotificationDAOImpl extends AbstractDAOImpl<NotificationDTO> implements NotificationRepository {
    @Override
    protected Class<NotificationDTO> getEntityClazz() {
        return NotificationDTO.class;
    }

    @Override
    public List<NotificationDTO> getUserFutureNotifications(Long userId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId AND data.date >= :currentDate";
        TypedQuery<NotificationDTO> query = em.createQuery(queryTxt, NotificationDTO.class)
                .setParameter("userId", userId)
                .setParameter("currentDate", DateTimeUtil.getCurrentDate());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
