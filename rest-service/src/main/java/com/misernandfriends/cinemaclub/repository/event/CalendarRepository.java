package com.misernandfriends.cinemaclub.repository.event;

import com.misernandfriends.cinemaclub.model.event.CalendarDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface CalendarRepository extends AbstractRepository<CalendarDTO> {

    List<CalendarDTO> getUserCalendars(Long userId);

}
