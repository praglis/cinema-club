package com.misernandfriends.cinemaclub.repository.movie;

import com.misernandfriends.cinemaclub.model.movie.PremiereDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Map;

public interface PremiereRepository extends AbstractRepository<PremiereDTO> {
    Object searchFor(Long cinemaId, Map<String, String> params);
    boolean isPremierePresent(PremiereDTO premiere);
}
