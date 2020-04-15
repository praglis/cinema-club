package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.BugReport;

public interface MailService {
    void sendConfirmationEmail(UserDTO user);

    void sendChangePasswordEmail(UserDTO user);

    void sendBugReport(BugReport bugReport);
}
