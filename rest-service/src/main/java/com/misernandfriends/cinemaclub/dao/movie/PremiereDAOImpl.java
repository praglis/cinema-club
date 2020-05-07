package com.misernandfriends.cinemaclub.dao.movie;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.PremiereDTO;
import com.misernandfriends.cinemaclub.repository.movie.PremiereRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Repository
public class PremiereDAOImpl extends AbstractDAOImpl<PremiereDTO> implements PremiereRepository {
    @Override
    protected Class<PremiereDTO> getEntityClazz() {
        return PremiereDTO.class;
    }

    @Override
    public boolean isPremierePresent(PremiereDTO premiere) {
        String queryTxt = "SELECT COUNT(*) FROM " + getEntityName() + " data WHERE " +
                "data.cinema.id = :cinemaId AND data.movie.id = :movieId AND date = :movieDate";
        TypedQuery<Long> query = em.createQuery(queryTxt, Long.class)
                .setParameter("cinemaId", premiere.getCinema().getId())
                .setParameter("movieId", premiere.getMovie().getId())
                .setParameter("movieDate", premiere.getDate());
        return query.getSingleResult() >= 1;
    }

    @Override
    public List<PremiereDTO> searchFor(Long cinemaId, Map<String, String> params) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder queryTxt = new StringBuilder("SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.movie.infoRD IS NULL AND data.cinema.infoRD IS NULL AND data.cinema.id = :cinemaId");
        if (params.containsKey("fromDate")) {
            queryTxt.append(" AND ").append("date >= :fromDate");
        }
        if (params.containsKey("toDate")) {
            queryTxt.append(" AND ").append("date <= :toDate");
        }
        if (params.containsKey("onDay")) {
            queryTxt.append(" AND ").append("date = :onDay");
        }

        TypedQuery<PremiereDTO> query = em.createQuery(queryTxt.toString(), PremiereDTO.class)
                .setParameter("cinemaId", cinemaId);
        params.forEach((s, s2) -> {
            try {
                query.setParameter(s, sdf.parse(s2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return query.getResultList();
    }
}
