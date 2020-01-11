package com.misernandfriends.cinemaclub.dao.cinema;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CinemaDAOImpl extends AbstractDAOImpl<CinemaDTO> implements CinemaRepository {
    @Override
    protected Class<CinemaDTO> getEntityClazz() {
        return CinemaDTO.class;
    }

    @Override
    public Optional<CinemaDTO> getByName(String name) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.name = :name AND data.infoRD IS NULL";
        TypedQuery<CinemaDTO> query = em.createQuery(queryTxt, CinemaDTO.class)
                .setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CinemaDTO> searchFor(Map<String, String> params) {
        String[] table = {"name", "address.country", "address.state", "address.city", "address.streetName", "address.houseNumber"};

        StringBuilder queryTxt = new StringBuilder("SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.infoRD IS NULL ");
        for (String fieldPath : table) {
            String[] fieldSep = fieldPath.split("\\.");
            String fieldName = fieldSep[fieldSep.length - 1];
            if (params.containsKey(fieldName)) {
                queryTxt.append(" AND ").append("data.").append(fieldPath).append(" = :").append(fieldName);
            }
        }

        TypedQuery<CinemaDTO> query = em.createQuery(queryTxt.toString(), CinemaDTO.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
