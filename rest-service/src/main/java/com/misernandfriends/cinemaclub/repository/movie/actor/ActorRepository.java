package com.misernandfriends.cinemaclub.repository.movie.actor;

import com.misernandfriends.cinemaclub.model.movie.actor.ActorDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

public interface ActorRepository extends AbstractRepository<ActorDTO> {

    Optional<ActorDTO> getByUrlApi(String url);

}
