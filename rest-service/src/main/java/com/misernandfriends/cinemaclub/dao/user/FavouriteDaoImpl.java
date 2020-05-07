package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.repository.user.FavouriteRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class FavouriteDaoImpl extends AbstractDAOImpl<FavouriteDTO> implements FavouriteRepository {

    @Override
    protected Class<FavouriteDTO> getEntityClazz() {
        return FavouriteDTO.class;
    }

    @Override
    public List<FavouriteDTO> getUserFavourites(Long userId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.user.id = :userId AND data.infoRD is null";

        TypedQuery<FavouriteDTO> query = em.createQuery(queryTxt, FavouriteDTO.class);
        query.setParameter("userId", userId);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<FavouriteDTO> getByUrl(Long userId, String apiUrl) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.user.id = :userId AND data.movie.apiUrl = :url AND data.infoRD IS NULL";

        TypedQuery<FavouriteDTO> query = em.createQuery(queryTxt, FavouriteDTO.class);
        query.setParameter("userId", userId);
        query.setParameter("url", apiUrl);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteFavourite(Long userId, String movieUrl) {
        Optional<FavouriteDTO> existingFavourite = getByUrl(userId, movieUrl);
        if (existingFavourite.isPresent()) {
            FavouriteDTO favourite = existingFavourite.get();
            favourite.setInfoRD(new Date());
            update(favourite);
        }
    }

    @Override
    public List<FavouriteDTO> getUserFavourites(Long userId, Date fromDate, int maxResults) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.user.id = :userId AND data.infoRD is null AND data.infoCD >= :fromDate";

        TypedQuery<FavouriteDTO> query = em.createQuery(queryTxt, FavouriteDTO.class)
                .setMaxResults(maxResults)
                .setParameter("fromDate", fromDate)
                .setParameter("userId", userId);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
