package com.misernandfriends.cinemaclub.repository.event;

import com.misernandfriends.cinemaclub.model.event.NotificationDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface NotificationRepository extends AbstractRepository<NotificationDTO> {

    public List<NotificationDTO> getUserFutureNotifications(Long userId);

}
