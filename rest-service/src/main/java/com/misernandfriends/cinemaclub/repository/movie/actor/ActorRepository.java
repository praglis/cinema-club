package com.misernandfriends.cinemaclub.repository.movie.actor;

import com.misernandfriends.cinemaclub.model.movie.actor.ActorDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

public interface ActorRepository extends AbstractRepository<ActorDTO> {

    public ActorDTO getByUrlApi(String url);

}
