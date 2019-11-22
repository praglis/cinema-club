package com.misernandfriends.cinemaclub.DAO.cinema;

import com.misernandfriends.cinemaclub.DAO.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CinemaDAOImpl extends AbstractDAOImpl<CinemaDTO> implements CinemaRepository {
    @Override
    protected Class<CinemaDTO> getEntityClazz() {
        return CinemaDTO.class;
    }
}
