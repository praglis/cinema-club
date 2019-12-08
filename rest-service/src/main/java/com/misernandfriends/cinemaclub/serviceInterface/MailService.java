package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.UserDTO;

public interface MailService {
    void sendConfirmationEmail(UserDTO user);
}
