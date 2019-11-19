package com.misernandfriends.cinemaclub.DAO.movie.actor;

import com.misernandfriends.cinemaclub.DAO.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.actor.ActorDTO;
import com.misernandfriends.cinemaclub.repository.movie.actor.ActorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class ActorDAOImpl extends AbstractDAOImpl<ActorDTO> implements ActorRepository {
    @Override
    protected Class<ActorDTO> getEntityClazz() {
        return ActorDTO.class;
    }

    @Override
    public ActorDTO getByUrlApi(String apiUrl) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.apiUrl = :apiUrl AND data.infoRD IS NULL";
        TypedQuery<ActorDTO> query = em.createQuery(queryTxt, ActorDTO.class)
                .setParameter("apiUrl", apiUrl);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
