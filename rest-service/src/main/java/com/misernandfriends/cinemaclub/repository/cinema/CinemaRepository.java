package com.misernandfriends.cinemaclub.repository.cinema;

import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CinemaRepository extends AbstractRepository<CinemaDTO> {
    Optional<CinemaDTO> getByName(String name);

    List<CinemaDTO> searchFor(Map<String, String> params);
}
