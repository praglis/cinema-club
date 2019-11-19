package com.misernandfriends.cinemaclub.DAO.event;

import com.misernandfriends.cinemaclub.DAO.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.event.CalendarDTO;
import com.misernandfriends.cinemaclub.repository.event.CalendarRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class CalendarDAOImpl extends AbstractDAOImpl<CalendarDTO> implements CalendarRepository {
    @Override
    protected Class<CalendarDTO> getEntityClazz() {
        return CalendarDTO.class;
    }

    @Override
    public List<CalendarDTO> getUserCalendars(Long userId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.user.id = :userId";
        TypedQuery<CalendarDTO> query = em.createQuery(queryTxt, CalendarDTO.class)
                .setParameter("userId", userId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
