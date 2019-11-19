package com.misernandfriends.cinemaclub.repository.event;

import com.misernandfriends.cinemaclub.model.event.EventDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface EventRepository extends AbstractRepository<EventDTO> {

    public List<EventDTO> getUserEvents(Long userId);

}
