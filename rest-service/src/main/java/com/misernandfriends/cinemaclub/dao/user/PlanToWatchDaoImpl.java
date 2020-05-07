package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.PlanToWatchMovieDTO;
import com.misernandfriends.cinemaclub.repository.user.PlanToWatchRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class PlanToWatchDaoImpl extends AbstractDAOImpl<PlanToWatchMovieDTO> implements PlanToWatchRepository {

    @Override
    protected Class<PlanToWatchMovieDTO> getEntityClazz() {
        return PlanToWatchMovieDTO.class;
    }

    @Override
    public List<PlanToWatchMovieDTO> getUserPlanToWatch(Long userId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.user.id = :userId AND data.infoRD is null";

        TypedQuery<PlanToWatchMovieDTO> query = em.createQuery(queryTxt, PlanToWatchMovieDTO.class);
        query.setParameter("userId", userId);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<PlanToWatchMovieDTO> getByUrl(Long userId, String apiUrl) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.user.id = :userId AND data.movie.apiUrl = :url AND data.infoRD IS NULL";

        TypedQuery<PlanToWatchMovieDTO> query = em.createQuery(queryTxt, PlanToWatchMovieDTO.class);
        query.setParameter("userId", userId);
        query.setParameter("url", apiUrl);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void deletePlanToWatch(Long userId, String movieUrl) {
        Optional<PlanToWatchMovieDTO> existingPlanToWatch = getByUrl(userId, movieUrl);
        if (existingPlanToWatch.isPresent()) {
            PlanToWatchMovieDTO ptw = existingPlanToWatch.get();
            ptw.setInfoRD(new Date());
            update(ptw);
        }
    }
}
