package com.misernandfriends.cinemaclub.dao.movie.actor;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.actor.ActorDTO;
import com.misernandfriends.cinemaclub.repository.movie.actor.ActorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ActorDAOImpl extends AbstractDAOImpl<ActorDTO> implements ActorRepository {
    @Override
    protected Class<ActorDTO> getEntityClazz() {
        return ActorDTO.class;
    }

    @Override
    public Optional<ActorDTO> getByUrlApi(String apiUrl) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.apiUrl = :apiUrl AND data.infoRD IS NULL";
        TypedQuery<ActorDTO> query = em.createQuery(queryTxt, ActorDTO.class)
                .setParameter("apiUrl", apiUrl);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public String getNameByUrlApi(String apiUrl) {
        Optional<ActorDTO> actorOptional = getByUrlApi(apiUrl);
        if (actorOptional.isPresent()) {
            return actorOptional.get().getName();
        } else {
            return "";
        }
    }
}
