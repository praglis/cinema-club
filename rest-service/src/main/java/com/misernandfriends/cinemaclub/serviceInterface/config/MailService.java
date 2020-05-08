package com.misernandfriends.cinemaclub.serviceInterface.config;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.config.BugReport;
import com.misernandfriends.cinemaclub.pojo.user.UserReport;

public interface MailService {
    void sendBugReport(BugReport bugReport);
    void sendChangePasswordEmail(UserDTO user);
    void sendConfirmationEmail(UserDTO user);
    void sendUserReport(UserReport userReport);
}
