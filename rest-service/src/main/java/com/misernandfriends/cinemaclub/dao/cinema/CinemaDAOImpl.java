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
        String orderBy = null;
        if (params.containsKey("orderBy")) {
            orderBy = params.get("orderBy");
            params.remove("orderBy");
        }
        String[] table = {"name", "address.country", "address.state", "address.city", "address.streetName", "address.houseNumber"};
        boolean isQueryEmpty = true;
        StringBuilder queryTxt = new StringBuilder("SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.infoRD IS NULL AND (");
        for (String fieldPath : table) {
            String[] fieldSep = fieldPath.split("\\.");
            String fieldName = fieldSep[fieldSep.length - 1];

            if (params.containsKey(fieldName)) {
                isQueryEmpty = false;
                queryTxt.append("LOWER(data.").append(fieldPath).append(") LIKE LOWER(:").append(fieldName).append(") OR ");
            }
        }
        if (isQueryEmpty) {
            queryTxt.delete(queryTxt.length() - 5, queryTxt.length());
        } else {
            queryTxt.delete(queryTxt.length() - 4, queryTxt.length());
            queryTxt.append(")");
        }
        queryTxt.append("order by ");
        if ("rating".equals(orderBy)) {
            queryTxt.append("data.rating DESC,");
        }
        queryTxt.append("data.name");

        TypedQuery<CinemaDTO> query = em.createQuery(queryTxt.toString(), CinemaDTO.class);
        params.forEach((k, v) -> query.setParameter(k, "%" + v + "%"));

        return query.getResultList();
    }
}
