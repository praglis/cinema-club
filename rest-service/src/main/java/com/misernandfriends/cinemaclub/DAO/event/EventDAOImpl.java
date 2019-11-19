package com.misernandfriends.cinemaclub.DAO.event;

import com.misernandfriends.cinemaclub.DAO.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.event.EventDTO;
import com.misernandfriends.cinemaclub.repository.event.EventRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class EventDAOImpl extends AbstractDAOImpl<EventDTO> implements EventRepository {

    @Override
    protected Class<EventDTO> getEntityClazz() {
        return EventDTO.class;
    }

    @Override
    public List<EventDTO> getUserEvents(Long userId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.user.id = :userId AND data.infoRD IS NULL";
        TypedQuery<EventDTO> query = em.createQuery(queryTxt, EventDTO.class)
                .setParameter("userId", userId);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
